package query.search;

import db.Neo4jUtil;
import db.PostgresUtil;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.graphdb.*;
import query.backtracking.BackTrackConstraints;
import datamodel.EntityNode;
import datamodel.EventEdge;
import executor.ExecutionContext;
import org.jgrapht.graph.DirectedPseudograph;
import static query.Utils.*;
import query.parser.ConstraintExpression;
import scala.collection.immutable.Stream;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class SearchRemoteRaw implements Search {
    public Connection conn;

    public SearchRemoteRaw() {}
    public SearchRemoteRaw(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void setup() throws Exception {
        if (ExecutionContext.getDbType() == ExecutionContext.DbType.Postgres){
            if (this.conn == null) this.conn = PostgresUtil.getConnection();
        }
    }

    public void setup(String dbName) throws Exception {
        if (ExecutionContext.getDbType() == ExecutionContext.DbType.Postgres){
            if (this.conn == null) this.conn = PostgresUtil.getConnection(dbName);
        } else {
            Neo4jUtil.db = dbName;
        }
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> search(SearchConstraints constraints) throws IOException {
        if(ExecutionContext.getDbType() == ExecutionContext.DbType.Postgres) {
            return searchSQL(constraints);
        } else {
            return searchCypher(constraints);
        }
    }

    public DirectedPseudograph<EntityNode, EventEdge> searchSQL(SearchConstraints constraints) throws IOException {
        try {
            long sTime = System.currentTimeMillis();
            // Initiate param lists
            LinkedList<String> nodeParams1 = new LinkedList<>();
            LinkedList<String> nodeParams2 = new LinkedList<>();
            LinkedList<String> opTypeParam = new LinkedList<>();
            // Compose sql query
            String sql = getAllNodeSQL();
            sql += getAllEdgeSQL();
            sql += getEvents(constraints, nodeParams1, nodeParams2, opTypeParam);
            sql += getResults(constraints);
            sql += "SELECT * FROM result;";

            // Print composed sql query
            System.out.printf("--> Raw SQL query: (length %d)%n", sql.length());
            System.out.println(sql);
            System.out.println("---");
            System.out.println(nodeParams1);
            System.out.println(nodeParams2);
            System.out.println(opTypeParam);
            PreparedStatement edgeStmt = conn.prepareStatement(sql);
            edgeStmt.setQueryTimeout(1800);

            int pos = 1;
            int nodeIdx1 = 0;
            int nodeIdx2 = 0;
            ArrayList<String> allTypes = new ArrayList(Arrays.asList("process", "file", "network"));
            for (int i=0; i<opTypeParam.size(); i++) {
                String param;
                do {
                    param = nodeParams1.get(nodeIdx1++);
                    edgeStmt.setString(pos++, param);
                } while (! allTypes.contains(param));
//                System.out.println("=============" + param.toString());
                do {
                    param = nodeParams2.get(nodeIdx2++);
                    edgeStmt.setString(pos++, param);
                } while (! allTypes.contains(param));
//                System.out.println("=============" + param.toString());
                edgeStmt.setString(pos++, opTypeParam.get(i));
            }

            edgeStmt.execute();
            ResultSet edgeResults = edgeStmt.getResultSet();
            ResultSetMetaData rsmd = edgeResults.getMetaData();
            int count = rsmd.getColumnCount()/8;

            HashMap<Integer, EntityNode> nodeMap = new HashMap<>();
            LinkedList<EventEdge> edges = new LinkedList<>();

            while (edgeResults.next()) {
                for (int i=0; i<count; i++) {
                    long id = edgeResults.getLong(8*i + 1);
                    int srcId = edgeResults.getInt(8*i + 2);
                    int dstId = edgeResults.getInt(8*i + 3);
                    BigDecimal startTime = edgeResults.getBigDecimal(8*i + 4);
                    BigDecimal endTime = edgeResults.getBigDecimal(8*i + 5);
                    String hostName = edgeResults.getString(8*i + 6);
                    String opType = edgeResults.getString(8*i + 7);
                    long amount = edgeResults.getLong(8*i + 8);

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
            System.out.println("Finished search" + " in " + (eTime - sTime) + "ms");
            return graph;
        } catch (SQLTimeoutException e) {
            System.out.println("Finished search" + " in " + "-1ms");
            return new DirectedPseudograph<>(EventEdge.class);
        } catch (Exception e) {
            System.out.println("Error Happened!!!");
            System.out.println("Finished search" + " in " + "-1ms");
//            e.printStackTrace();
            return new DirectedPseudograph<>(EventEdge.class);
        }
    }

    public DirectedPseudograph<EntityNode, EventEdge> searchCypher(SearchConstraints constraints) throws IOException {
        DirectedPseudograph<EntityNode, EventEdge> graph = new DirectedPseudograph<>(EventEdge.class);
        DatabaseManagementService managementService = Neo4jUtil.connectDb(Neo4jUtil.db);
        GraphDatabaseService graphDb = managementService.database(Neo4jUtil.db);
        long startTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> relations = constraints.getEdges();
        HashMap<String, ConstraintExpression> nodeConst = constraints.getNodeConstraints();
        ArrayList<List<String>> opts = constraints.getEdgeConstraints();
        System.out.println(relations);
        System.out.println(nodeConst);
        System.out.println(opts);
        // Add edge match
        StringBuilder query = new StringBuilder("MATCH ");
        int idx = 0;
        for (ArrayList<String> relation : relations) {
            if (idx != 0) {
                query.append(", ");
            }
            // Get relation
            String source = relation.get(0);
            String opt = relation.get(1);
            String sink = relation.get(2);
            query.append(String.format("(%s)-[%s]->(%s)", source, "event" + idx, sink));
            idx += 1;
        }
        // The params of cypher query
        Map<String, Object> params = new HashMap<>();
        query.append("\n").append("WHERE ");
        // Add node match
        for (String node : nodeConst.keySet()) {
            ConstraintExpression nc = nodeConst.get(node);
            String nodeMatch = nc.toCypher(node, params);
            query.append(nodeMatch).append(" AND\n");
        }
        // Add relations between edges
        idx = 0;
        for (List<String> opt : opts) {
            if (Objects.equals(opt.get(0), "and")) {
                String eventSource = "event" + idx;
                String eventSink = "event" + (idx + 1);
                double time = getInterval(opt) * 1000.0;
                query.append(String.format("(%s.endtime > %s.starttime - %s) AND\n", eventSource, eventSink, time));
            }
            idx += 1;
        }
        query.append("true\n");
        // Add return
        query.append("RETURN ");
        for (String nodeID : nodeConst.keySet()) {
            query.append(nodeID).append(", ");
        }
        int i;
        for (i=0; i<relations.size()-1; i++) {
            query.append(String.format("event%s, ", i));
        }
        query.append(String.format("event%s", i));
        // Print composed sql query
        System.out.printf("--> Raw Cypher query: (length %d)%n", query.length());
        System.out.println(">>>Cypher query:");
        System.out.println(query);
        System.out.println("<<<");
        System.out.println(">>>>>>>>>Params:");
        System.out.println(params);

        ExecutorService exec = Executors.newFixedThreadPool(1);
        try (Transaction tran = graphDb.beginTx()) {
            // run query
            System.out.println(">>>>>>>>>Running query...");
//            Result result = tran.execute(cypher, params);
            Future<Result> future = exec.submit(() -> {
                return tran.execute(query.toString(), params);
            });
            Result result = future.get(1800, TimeUnit.SECONDS);
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                idx = 0;
                for (ArrayList<String> relation : relations) {
                    // Get relation
                    String sourceID = relation.get(0);
                    String sinkID = relation.get(2);
                    EntityNode source = createNode((Node) row.get(sourceID));
                    EntityNode sink = createNode((Node) row.get(sinkID));
                    EventEdge edge = createEdge(source, sink, (Relationship) row.get("event"+idx));
                    graph.addVertex(source);
                    graph.addVertex(sink);
                    graph.addEdge(source, sink, edge);
                    idx += 1;
                }
            }
//            System.out.printf(">>>>>>>>>>Output: %s lines\n", count);
            long endTime = System.currentTimeMillis();
            System.out.println("Finished search" + " in " + (endTime - startTime) + "ms");
            return graph;
        } catch (ExecutionException e) {
            System.out.println("ExecutionException in trackPOIEventNeo4j");
            System.out.println("Finished search" + " in " + "-1ms");
            return graph;
        } catch (TimeoutException e) {
            System.out.println("TimeoutException in trackPOIEventNeo4j");
            System.out.println("Finished search" + " in " + "-1ms");
            return graph;
        } catch (Exception e) {
            System.out.println("Exception in trackPOIEventNeo4j");
            System.out.println("Finished search" + " in " + "-1ms");
            return graph;
        }
        finally {
            exec.shutdown();
            Neo4jUtil.shutDown(managementService);
        }
    }

    public String getAllNodeSQL() {
        String query="allnodes (type, id, name, path, dstip, dstport, srcip, srcport, pid, exename, exepath, cmdline) AS (\n" +
                "SELECT 'file', id, name, path, NULL::text, NULL::int, NULL::text, NULL::int, NULL::int, NULL::text, NULL::text, NULL::text FROM file UNION\n" +
                "SELECT 'network', id, NULL::text, NULL::text, CAST (dstip AS text), dstport, CAST (srcip AS text), srcport, NULL::int, NULL::text, NULL::text, NULL::text FROM network UNION\n" +
                "SELECT 'process', id, NULL::text, NULL::text, NULL::text, NULL::int, NULL::text, NULL::int, pid, exename, exepath, cmdline FROM process),\n" +
                "nodes AS (SELECT * FROM allnodes), ";
        return String.format("WITH %s", query);
    }

    public String getAllEdgeSQL() {
        String query="alledges AS (\n" +
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM fileevent UNION\n" +
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM networkevent UNION\n" +
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, 0 AS amount FROM processevent ),\n" +
                "edges AS (SELECT e.* FROM alledges e),\n";
        return query;
    }


    // Search events based on constraints
    public String getEvents(SearchConstraints constraints, LinkedList<String> nodeParams1,
                            LinkedList<String> nodeParams2,
                            LinkedList<String> opTypeParam) {
        String query = "event%s (id, srcid, dstid, starttime, endtime, hostname, optype, amount) AS (\n" +
                "    SELECT edges.* FROM\n" +
                "    nodes n1 INNER JOIN edges ON n1.id = edges.srcid\n" +
                "    INNER JOIN nodes n2 ON edges.dstid = n2.id\n" +
                "    WHERE (%s) AND (%s) AND (%s)\n" +
                "),\n";
        String eventsql = "";
        ArrayList<ArrayList<String>> relations = constraints.getEdges();
        HashMap<String, ConstraintExpression> nodeConst = constraints.getNodeConstraints();
        int idx = 0;
        for (ArrayList<String> relation : relations) {
            idx += 1;
            // Get relation
            String source = relation.get(0);
            String opt = relation.get(1);
            String sink = relation.get(2);
            // Get node constraints
            ConstraintExpression sourceConst = nodeConst.get(source);
            ConstraintExpression sinkConst = nodeConst.get(sink);
            // Set table names
            sourceConst.setSqlTable("n1");
            sinkConst.setSqlTable("n2");
            // Get sql string
            String nodesql1 = sourceConst.toSQL(nodeParams1);
            String nodesql2 = sinkConst.toSQL(nodeParams2);
            String optsql = "true";
            if (opt.equals("null")) {
                optsql = "edges.optype != ?";
            } else {
                optsql = "edges.optype = ?";
            }
            opTypeParam.add(opt);
            eventsql += String.format(query, idx, nodesql1, nodesql2, optsql);
        }
        return eventsql;
    }

    // Filter events by cross join them
    public String getResults(SearchConstraints constraints) {
        int idx = 1;
        StringBuilder eventString = new StringBuilder("event1.*");
        StringBuilder crossJoinsql = new StringBuilder("result AS (\n" +
                "SELECT %s FROM event1 \n");
        ArrayList<List<String>> opts = constraints.getEdgeConstraints();
        // Build cross join clause: event1 cross join event2 cross join event3 ...
        for (List<String> opt : opts) {
            idx += 1;
            String event = "event" + idx;
            crossJoinsql.append(String.format("CROSS JOIN %s \n", event));
            eventString.append(String.format(", %s.*", event));
        }
        // Parse the connections between relations
        String consql = getConnection(constraints);
        // Build where clauses
        idx = 0;
        crossJoinsql.append("WHERE ");
        for (List<String> opt : opts) {
            idx += 1;
            if (Objects.equals(opt.get(0), "and")) {
                String eventSource = "event" + idx;
                String eventSink = "event" + (idx + 1);
                double time = getInterval(opt);
                crossJoinsql.append(String.format("(%s.endtime > %s.starttime - %s) AND \n", eventSource, eventSink, time));
            }
        }
        // 1. Fill the right side of AND
        // 2. Correspond to "OR" operation
        crossJoinsql.append("true");
        // Add connections
        if (consql != null) crossJoinsql.append(consql);
        // Add right bracket
        crossJoinsql.append(")\n");
        return String.format(crossJoinsql.toString(), eventString.toString());
    }

    public String getConnection(SearchConstraints constraints) {
        StringBuilder consql = new StringBuilder();
        ArrayList<ArrayList<String>> relations = constraints.getEdges();
        HashMap<String, ArrayList<ArrayList<String>>> entity2relation = new HashMap<>();
//        System.out.println("Relations: " + relations);
        if (relations.size() <= 1) return consql.toString();
        // Add the first relation
        entity2relation.put(relations.get(0).get(0), new ArrayList<ArrayList<String>>());
        entity2relation.get(relations.get(0).get(0)).add(new ArrayList<String>(Arrays.asList("event1", "srcid")));
        entity2relation.put(relations.get(0).get(2), new ArrayList<ArrayList<String>>());
        entity2relation.get(relations.get(0).get(2)).add(new ArrayList<String>(Arrays.asList("event1", "dstid")));
        for (int i=1; i<relations.size(); i++) {
            ArrayList<String> relation = relations.get(i);
            String srcNode = relation.get(0);
            String dstNode = relation.get(2);
            String curEvent = String.format("event%d", i+1);
            // Add source connection
            if (entity2relation.containsKey(srcNode)) {
                for (ArrayList<String> existNode : entity2relation.get(srcNode)) {
                    consql.append(String.format(" AND %s.srcid = %s", curEvent, existNode.get(0) + "." + existNode.get(1)));
                }
            } else {
                entity2relation.put(srcNode, new ArrayList<ArrayList<String>>());
            }
            entity2relation.get(srcNode).add(new ArrayList<>(Arrays.asList(curEvent, "srcid")));
            // Add destination connection
            if (entity2relation.containsKey(dstNode)) {
                for (ArrayList<String> existNode : entity2relation.get(dstNode)) {
                    consql.append(String.format(" AND %s.dstid = %s", curEvent, existNode.get(0) + "." + existNode.get(1)));
                }
            } else {
                entity2relation.put(dstNode, new ArrayList<ArrayList<String>>());
            }
            entity2relation.get(dstNode).add(new ArrayList<>(Arrays.asList(curEvent, "dstid")));
        }
        return consql.toString();
    }

    public String getUnionSQL(String query1, String query2) {
        return String.format("%s (%s \n UNION \n %s)", "SELECT * from", query1, query2);
    }
}
