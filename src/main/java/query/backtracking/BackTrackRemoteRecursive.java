package query.backtracking;

import org.jgrapht.graph.DirectedPseudograph;
import datamodel.*;
import db.PostgresUtil;
import db.Neo4jUtil;
import org.neo4j.graphdb.*;
import query.parser.ConstraintExpression;
import executor.ExecutionContext.DbType;
import executor.ExecutionContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;

import org.neo4j.dbms.api.DatabaseManagementService;

import static query.Utils.*;


public class BackTrackRemoteRecursive implements BackTrack {
    public Connection conn;
    private final int neo4jStepBound = 2;

    public BackTrackRemoteRecursive() {
    }

    public BackTrackRemoteRecursive(Connection conn0) {
        conn = conn0;
    }

    private DirectedPseudograph<EntityNode, EventEdge> trackPOIEvent(String edgeSql, BackTrackConstraints constraints) {
        try {
            long sTime = System.currentTimeMillis();
            LinkedList<String> nodeParams = new LinkedList<>();
            String nodeSql = getNodeSql(constraints, nodeParams);
            LinkedList<String> poiParams = new LinkedList<>();
            String poiSql = constraints.getPOIConstraints().toSQL(poiParams);
            LinkedList<String> edgeParams = new LinkedList<>();
            ConstraintExpression edgeConstraints = constraints.getEdgeConstraints();
            String edgeConstraintSql = edgeConstraints == null ? "" : ("WHERE " + edgeConstraints.toSQL(edgeParams));
            String timeConstraintSql = constraints.getTimeConstraintSecs() > 0 ? "AND clock_timestamp() < ?" : "";
            String stepConstraintSql = constraints.getStepConstraint() > 0 ? "AND step < ?" : "";

            edgeSql = String.format(edgeSql, nodeSql, edgeConstraintSql, poiSql, timeConstraintSql, stepConstraintSql);
            System.out.printf("--> Recursive SQL query: (length %d)%n", edgeSql.length());
            System.out.println(edgeSql);
            System.out.println("---");
            PreparedStatement edgeStmt = conn.prepareStatement(edgeSql);
            edgeStmt.setQueryTimeout(1800);

            int pos = 1;
            for (String param : nodeParams) {
                edgeStmt.setString(pos++, param);
            }
            for (String param : edgeParams) {
                edgeStmt.setString(pos++, param);
            }
            for (String param : poiParams) {
                edgeStmt.setString(pos++, param);
            }
            if (constraints.getTimeConstraintSecs() > 0) {
                Timestamp ts = new Timestamp(System.currentTimeMillis() + constraints.getTimeConstraintSecs() * 1000);
                edgeStmt.setTimestamp(pos++, ts);
            }
            if (constraints.getStepConstraint() > 0) {
                edgeStmt.setInt(pos, constraints.getStepConstraint());
            }

            edgeStmt.execute();
            ResultSet edgeResults = edgeStmt.getResultSet();

            HashMap<Integer, EntityNode> nodeMap = new HashMap<>();
            LinkedList<EventEdge> edges = new LinkedList<>();

            while (edgeResults.next()) {
                long id = edgeResults.getLong(1);
                int srcId = edgeResults.getInt(2);
                int dstId = edgeResults.getInt(3);
                BigDecimal startTime = edgeResults.getBigDecimal(4);
                BigDecimal endTime = edgeResults.getBigDecimal(5);
                String hostName = edgeResults.getString(6);
                String opType = edgeResults.getString(7);
                long amount = edgeResults.getLong(8);

                EntityNode source, sink;
                if (nodeMap.containsKey(srcId)) {
                    source = nodeMap.get(srcId);
                } else {
                    source = new EntityNode(srcId);
                    nodeMap.put(srcId, source);
                }
                if (nodeMap.containsKey(dstId)) {
                    sink = nodeMap.get(dstId);
                } else {
                    sink = new EntityNode(dstId);
                    nodeMap.put(dstId, sink);
                }

                EventEdge edge = new EventEdge(source, sink, id, opType, amount, startTime, endTime, hostName);
                edges.add(edge);
            }

            extractFileNodes(this.conn, nodeMap);
            extractNetworkNodes(this.conn, nodeMap);
            extractProcessNodes(this.conn, nodeMap);

            DirectedPseudograph<EntityNode, EventEdge> graph = new DirectedPseudograph<>(EventEdge.class);
            for (EntityNode node : nodeMap.values()) {
                graph.addVertex(node);
            }
            for (EventEdge edge : edges) {
                graph.addEdge(edge.getSource(), edge.getSink(), edge);
            }
            long eTime = System.currentTimeMillis();
            System.out.println("Finished tracking" + " in " + (eTime - sTime) + "ms");
            return graph;
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException in trackPOIEvent");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return new DirectedPseudograph<>(EventEdge.class);
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Finished tracking" + " in " + "-1ms");
            return new DirectedPseudograph<>(EventEdge.class);
        }
    }

    private String getNodeSql(BackTrackConstraints constraints, LinkedList<String> params) {
        ConstraintExpression nodeConstraints = constraints.getNodeConstraints();
        return String.join("\n",
                "WITH RECURSIVE allnodes (type, id, name, path, dstip, dstport, srcip, srcport, pid, exename, exepath, cmdline) AS (",
                "SELECT 'file', id, name, path, NULL::text, NULL::int, NULL::text, NULL::int, NULL::int, NULL::text, NULL::text, NULL::text FROM file UNION",
                "SELECT 'network', id, NULL::text, NULL::text, CAST (dstip AS text), dstport, CAST (srcip AS text), srcport, NULL::int, NULL::text, NULL::text, NULL::text FROM network UNION",
                "SELECT 'process', id, NULL::text, NULL::text, NULL::text, NULL::int, NULL::text, NULL::int, pid, exename, exepath, cmdline FROM process),",
                "nodes AS (SELECT * FROM allnodes",
                nodeConstraints == null ? ")," : String.format(" WHERE %s ),", nodeConstraints.toSQL(params))
        );
    }

    @Override
    public void setup() throws Exception {
        if (ExecutionContext.getDbType() == DbType.Postgres){
            if (conn == null) conn = PostgresUtil.getConnection();
        }
    }

    public void setup(String dbName) throws Exception {
        if (ExecutionContext.getDbType() == DbType.Postgres){
            if (conn == null) conn = PostgresUtil.getConnection(dbName);
        } else {
            Neo4jUtil.db = dbName;
        }
    }

    public DirectedPseudograph<EntityNode, EventEdge> backTrackPOIEvent(BackTrackConstraints constraints) throws IOException {
        if(ExecutionContext.getDbType() == DbType.Postgres) {
            return backTrackPOIEventPostgres(constraints);
        } else {
            return backTrackPOIEventNeo4j(constraints);
        }
    }

    private DirectedPseudograph<EntityNode, EventEdge> backTrackPOIEventNeo4j(BackTrackConstraints constraints) throws IOException{
        String cypherFrame = "MATCH ()-[r]->(root)\n" +
                "WHERE %s \n" +
                "SET r.threshold = r.endtime, r.marked=true\n" +
                "WITH root\n" +
                "MATCH p = ()-[*..%s]->(root)\n" +
                "WITH DISTINCT relationships(p) as r\n" +
                "FOREACH (i IN reverse(range(0, size(r)-2))\n" +
                "| FOREACH (n1 IN [r[i]]\n" +
                "| FOREACH (n2 IN [r[i+1]]\n" +
                "| FOREACH (edge IN\n" +
                "    CASE\n" +
                "    WHEN n1.starttime <= n2.threshold THEN [n1]\n" +
                "    ELSE []\n" +
                "    END | SET edge.marked=true\n" +
                "          SET n1.threshold=CASE \n" +
                "                            WHEN n1.endtime > n2.threshold THEN n2.threshold\n" +
                "                            ELSE n1.endtime END\n" +
                "    ))))\n" +
                "WITH DISTINCT r\n" +
                "MATCH (sn)-[rr]->(en)\n" +
                "WHERE (rr IN r AND rr.marked=true)\n" +
                "   AND (%s) AND (%s)\n" +
                "   AND (%s)\n" +
                "RETURN DISTINCT sn, rr, en";
        return trackPOIEventNeo4j(constraints, cypherFrame);
    }

    private DirectedPseudograph<EntityNode, EventEdge> backTrackPOIEventPostgres(BackTrackConstraints constraints){
        String edgeSql = String.join("\n",
                "%s",
                "alledges AS (",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM fileevent UNION",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM networkevent UNION",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, 0 AS amount FROM processevent ),",
                "edges AS (SELECT e.* FROM alledges e INNER JOIN nodes n1 ON e.srcid = n1.id",
                "INNER JOIN nodes n2 ON e.dstid = n2.id %s ),",
                "graph (id, srcid, dstid, starttime, endtime, hostname, optype, amount, threshold, step) AS (",
                "SELECT *, edges.endtime, 1 FROM edges",
                "WHERE dstid IN (SELECT id FROM nodes WHERE %s)",
                "UNION SELECT edges.*, LEAST(edges.endtime, graph.threshold),",
                "graph.step" + (constraints.getStepConstraint() > 0 ? "+1" : ""),
                "FROM edges JOIN graph ON edges.dstid = graph.srcid",
                "WHERE edges.starttime <= graph.threshold %s %s )",
                "SELECT DISTINCT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM graph;"
        );
        return trackPOIEvent(edgeSql, constraints);
    }

    public DirectedPseudograph<EntityNode, EventEdge> forwardTrackPOIEvent(BackTrackConstraints constraints) throws IOException {
        if(ExecutionContext.getDbType() == DbType.Postgres) {
            return forwardTrackPOIEventPostgres(constraints);
        } else{
            return forwardTrackPOIEventNeo4j(constraints);
        }
    }

    private DirectedPseudograph<EntityNode, EventEdge> forwardTrackPOIEventNeo4j(BackTrackConstraints constraints) throws IOException{
        String cypherFrame = "MATCH (root)-[r]->()\n" +
                "WHERE %s \n" +
                "SET r.threshold = r.starttime, r.marked=true\n" +
                "WITH root\n" +
                "MATCH p = (root)-[*..%s]->()\n" +
                "WITH DISTINCT relationships(p) as r\n" +
                "FOREACH (i IN range(0, size(r)-2)\n" +
                "| FOREACH (n1 IN [r[i]]\n" +
                "| FOREACH (n2 IN [r[i+1]]\n" +
                "| FOREACH (edge IN\n" +
                "    CASE\n" +
                "    WHEN n1.threshold <= n2.endtime THEN [n2]\n" +
                "    ELSE []\n" +
                "    END | SET edge.marked=true\n" +
                "          SET n2.threshold=CASE \n" +
                "                            WHEN n1.threshold > n2.starttime THEN n1.threshold\n" +
                "                            ELSE n2.starttime END\n" +
                "    ))))\n" +
                "WITH DISTINCT r\n" +
                "MATCH (sn)-[rr]->(en)\n" +
                "WHERE (rr IN r AND rr.marked=true)\n" +
                "   AND (%s) AND (%s)\n" +
                "   AND (%s)\n" +
                "RETURN DISTINCT sn, rr, en";
        return trackPOIEventNeo4j(constraints, cypherFrame);
    }

    private DirectedPseudograph<EntityNode, EventEdge> forwardTrackPOIEventPostgres(BackTrackConstraints constraints){
        String edgeSql = String.join("\n",
                "%s",
                "alledges AS (",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM fileevent UNION",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM networkevent UNION",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, 0 AS amount FROM processevent ),",
                "edges AS (SELECT e.* FROM alledges e %s ),",
                "graph (id, srcid, dstid, starttime, endtime, hostname, optype, amount, threshold, step) AS (",
                "SELECT *, edges.starttime, 1 FROM edges",
                "WHERE srcid IN (SELECT id FROM nodes WHERE %s) AND dstid IN (SELECT id FROM nodes)",
                "UNION SELECT edges.*, GREATEST(edges.starttime, graph.threshold),",
                "graph.step" + (constraints.getStepConstraint() > 0 ? "+1" : ""),
                "FROM edges JOIN graph ON edges.srcid = graph.dstid",
                "WHERE edges.endtime >= graph.threshold %s %s AND edges.srcid IN (SELECT id FROM nodes) AND edges.dstid IN (SELECT id FROM nodes) )",
                "SELECT DISTINCT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM graph;"
        );
        return trackPOIEvent(edgeSql, constraints);
    }

    private DirectedPseudograph<EntityNode, EventEdge> trackPOIEventNeo4j(BackTrackConstraints constraints, String cypherFrame) throws IOException{
        DirectedPseudograph<EntityNode, EventEdge> graph = new DirectedPseudograph<>(EventEdge.class);
        DatabaseManagementService managementService = Neo4jUtil.connectDb(Neo4jUtil.db);
        GraphDatabaseService graphDb = managementService.database(Neo4jUtil.db);
        long startTime = System.currentTimeMillis();
        // The params of cypher query
        Map<String, Object> params = new HashMap<>();
        String cleanCypher = "MATCH ()-[r]->() SET r.marked=false REMOVE r.threshold\n";

        String poiCypher = constraints.getPOIConstraints().toCypher("root", params);
        String snodeCypher="true", enodeCypher="true";
        String edgeCypher="true";
        if (constraints.getNodeConstraints() != null) {
            snodeCypher = constraints.getNodeConstraints().toCypher("sn", params);
            enodeCypher = constraints.getNodeConstraints().toCypher("en", params);
        }
        if (constraints.getEdgeConstraints() != null) {
            edgeCypher = constraints.getEdgeConstraints().toCypher("rr", params);
        }
        int stepParam = constraints.getStepConstraint()>0 && constraints.getStepConstraint()<=neo4jStepBound? constraints.getStepConstraint():neo4jStepBound;
        String cypher = String.format(cypherFrame, poiCypher, stepParam, snodeCypher, enodeCypher, edgeCypher);
        params.put("stepConstrain", stepParam);
        System.out.println(">>>Cypher query:");
        System.out.println(cypher);
        System.out.println("<<<");
        System.out.println(">>>>>>>>>Params:");
        System.out.println(params);

        ExecutorService exec = Executors.newFixedThreadPool(1);
        try (Transaction tran = graphDb.beginTx()) {
            // Reset database
            System.out.println(">>>>>>>>>Resetting graph database...");
            tran.execute(cleanCypher);
            // run query
            System.out.println(">>>>>>>>>Running query...");
//            Result result = tran.execute(cypher, params);
            Future<Result> future = exec.submit(() -> {
                return tran.execute(cypher, params);
            });
            Result result = future.get(1800, TimeUnit.SECONDS);
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                EntityNode source = createNode((Node) row.get("sn"));
                EntityNode sink = createNode((Node) row.get("en"));
                EventEdge edge = createEdge(source, sink, (Relationship) row.get("rr"));
                graph.addVertex(source);
                graph.addVertex(sink);
                graph.addEdge(source, sink, edge);
            }
//            System.out.printf(">>>>>>>>>>Output: %s lines\n", count);
            long endTime = System.currentTimeMillis();
            System.out.println("Finished tracking" + " in " + (endTime - startTime) + "ms");
            return graph;
        } catch (ExecutionException e) {
            System.out.println("ExecutionException in trackPOIEventNeo4j");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        } catch (TimeoutException e) {
            System.out.println("TimeoutException in trackPOIEventNeo4j");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        } catch (Exception e) {
            System.out.println("Exception in trackPOIEventNeo4j");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        }
        finally {
            exec.shutdown();
            Neo4jUtil.shutDown(managementService);
        }
    }
}
