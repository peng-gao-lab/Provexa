package query.backtracking;

import datamodel.*;
import db.Neo4jUtil;
import db.PostgresUtil;
import org.jgrapht.graph.DirectedPseudograph;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.graphdb.*;
import executor.ExecutionContext;
import executor.ExecutionContext.DbType;
import query.parser.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import static query.Utils.createEdge;
import static query.Utils.createNode;

public class BackTrackRemote implements BackTrack {
    private final HashMap<Long, EntityNode> nodes;
    private final HashMap<Long, Integer> steps;
    private final HashMap<Long, BigDecimal> timeThresholds;
    private final LinkedList<EventEdge> queue;
    private final HashSet<EntityNode> nodeInTheQueue;
    private final DirectedPseudograph<EntityNode, EventEdge> graph;
    public Connection conn;
    DatabaseManagementService managementService;
    GraphDatabaseService graphDb;

    public BackTrackRemote(){
        nodes = new HashMap<>();
        steps = new HashMap<>();
        timeThresholds = new HashMap<>();
        queue = new LinkedList<>();
        nodeInTheQueue = new HashSet<>();
        graph = new DirectedPseudograph<>(EventEdge.class);
    }

    private void neo4jConnect() throws IOException {
        this.managementService = Neo4jUtil.connectDb(Neo4jUtil.db);
        this.graphDb = this.managementService.database(Neo4jUtil.db);
    }

    public BackTrackRemote(Connection conn0) {
        this();
        conn = conn0;
    }

    @Override
    public void setup() throws Exception {
        if (ExecutionContext.getDbType() == DbType.Postgres) {
            if (conn == null) conn = PostgresUtil.getConnection();
        }
    }

    public void setup(String dbName) throws Exception {
        if (ExecutionContext.getDbType() == DbType.Postgres) {
            if (conn == null) conn = PostgresUtil.getConnection(dbName);
        }
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> backTrackPOIEvent(BackTrackConstraints constraints) throws IOException {
//        System.out.println("backTrackPOIEvent invoked in " + this.getClass().getName());
        if (ExecutionContext.getDbType() == DbType.Neo4j) {
            neo4jConnect();
        }
        long sTime = System.currentTimeMillis();
        int stepConstraint = constraints.getStepConstraint();
        long endTime = constraints.getTimeConstraintSecs();
        endTime = endTime!=-1?endTime:1800; // Set max time to 30 minutes.
        if (endTime > 0) endTime = System.currentTimeMillis() + endTime * 1000;
        try {
            for (EntityNode node : getStartVertices(constraints)) {
                graph.addVertex(node);
                nodeInTheQueue.add(node);
                BigDecimal latest = BigDecimal.ZERO;
                // threshold = max(..., threshold[i], ...)
                for (EventEdge edge : getIncomingEdges(constraints, node, 0)) {
                    queue.add(edge);
                    if (latest.compareTo(edge.getEndTime()) < 0) {
                        latest = edge.getEndTime();
                    }
                }
                timeThresholds.put(node.getID(), latest);
            }
            while (!queue.isEmpty()) {
                if (endTime > 0 && System.currentTimeMillis() > endTime) {
                    System.out.println("Finished tracking" + " in " + "0ms");
                    System.out.println("Exceed time constraint in backtrackPOIEvent");
                    return graph;
                }
                EventEdge edge = queue.poll();
                EntityNode sink = edge.getSink();
                BigDecimal sinkThreshold = timeThresholds.get(sink.getID());
                if (edge.getStartTime().compareTo(sinkThreshold) > 0) continue;
                EntityNode source = edge.getSource();
                int step = steps.get(edge.getID());
                if (stepConstraint > 0 && step > stepConstraint) continue;
                if (!nodeInTheQueue.contains(source)) {
                    nodeInTheQueue.add(source);
                    graph.addVertex(source);
                    queue.addAll(getIncomingEdges(constraints, source, step));
                    timeThresholds.put(source.getID(), BigDecimal.ZERO);
                }
                // threahold = min(endTime, sinkThreshold)
                BigDecimal thresholdForSource = edge.endTime.min(sinkThreshold);
                // threshold = max(..., threshold[i], ...)
                if(timeThresholds.get(source.getID()).compareTo(thresholdForSource) < 0){
                    timeThresholds.put(source.getID(), thresholdForSource);
                }
                graph.addEdge(source, sink, edge);
            }
            long eTime = System.currentTimeMillis();
            System.out.println("Finished tracking" + " in " + (eTime - sTime) + "ms");
            return graph;
        } catch (QueryExecutionException e) {
            System.out.println("QueryExecutionException in backtrackPOIEvent");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError in backtrackPOIEvent");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        } catch (Exception e) {
            e.printStackTrace();
            return graph;
        } finally {
            if (ExecutionContext.getDbType() == DbType.Neo4j) {
                Neo4jUtil.shutDown(this.managementService);
            }
        }
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> forwardTrackPOIEvent(BackTrackConstraints constraints) throws IOException {
//        System.out.println("forwardTrackPOIEvent invoked in " + this.getClass().getName());
        if (ExecutionContext.getDbType() == DbType.Neo4j) {
            neo4jConnect();
        }
        long sTime = System.currentTimeMillis();
        int stepConstraint = constraints.getStepConstraint();
        long endTime = constraints.getTimeConstraintSecs();
        endTime = endTime!=-1?endTime:3600; // Set max time to 1 hour.
        if (endTime > 0) endTime = System.currentTimeMillis() + endTime * 1000;
        try {
            for (EntityNode node : getStartVertices(constraints)) {
                graph.addVertex(node);
                nodeInTheQueue.add(node);
                BigDecimal first = null;
                // threshold = min(..., threshold[i], ...)
                for (EventEdge edge : getOutgoingEdges(constraints, node, 0)) {
                    queue.add(edge);
                    if (first == null || first.compareTo(edge.getStartTime()) > 0) {
                        first = edge.getStartTime();
                    }
                }
                timeThresholds.put(node.getID(), first == null ? BigDecimal.ZERO : first);
            }
            while (!queue.isEmpty()) {
                if (endTime > 0 && System.currentTimeMillis() > endTime) {
                    System.out.println("Finished tracking" + " in " + "0ms");
                    System.out.println("Exceed time constraint in forwardtrackPOIEvent");
                    return graph;
                }
                EventEdge edge = queue.poll();
                EntityNode source = edge.getSource();
                BigDecimal sourceThreshold = timeThresholds.get(source.getID());
                if (edge.getEndTime().compareTo(sourceThreshold) < 0) continue;
                EntityNode sink = edge.getSink();
                int step = steps.get(edge.getID());
                if (stepConstraint > 0 && step > stepConstraint) continue;
                if (!nodeInTheQueue.contains(sink)) {
                    nodeInTheQueue.add(sink);
                    graph.addVertex(sink);
                    queue.addAll(getOutgoingEdges(constraints, sink, step));
                    timeThresholds.put(sink.getID(), new BigDecimal(Long.MAX_VALUE));
                }
                // threahold = max(startTime, sourceThreshold)
                BigDecimal thresholdForSink = edge.startTime.max(sourceThreshold);
                // threshold = min(..., threshold[i], ...)
                if(timeThresholds.get(sink.getID()).compareTo(thresholdForSink) > 0){
                    timeThresholds.put(sink.getID(), thresholdForSink);
                }
                graph.addEdge(source, sink, edge);
            }
            long eTime = System.currentTimeMillis();
            System.out.println("Finished tracking" + " in " + (eTime - sTime) + "ms");
            return graph;
        } catch (QueryExecutionException e) {
            System.out.println("QueryExecutionException in forwardtrackPOIEvent");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError in forwardtrackPOIEvent");
            System.out.println("Finished tracking" + " in " + "-1ms");
            return graph;
        } catch (Exception e) {
            e.printStackTrace();
            return graph;
        } finally {
            if (ExecutionContext.getDbType() == DbType.Neo4j) {
                Neo4jUtil.shutDown(this.managementService);
            }
        }
    }

    private LinkedList<EventEdge> getIncomingEdges(BackTrackConstraints constraints, EntityNode node, int step) throws SQLException {
        long id = node.getID();
        if (ExecutionContext.getDbType() == DbType.Postgres) {
            ConstraintExpression relationalConstraints = new BinaryExpression("dstid", BinaryOperator.Equal, id);
            return getEdges(constraints, relationalConstraints, step);
        } else {
            ConstraintExpression rootConstraint = new BinaryExpression("id", BinaryOperator.Equal, id);
            return getEdgesCypher(constraints, rootConstraint, step, true);
        }
    }

    private LinkedList<EventEdge> getOutgoingEdges(BackTrackConstraints constraints, EntityNode node, int step) throws SQLException {
        long id = node.getID();
        if (ExecutionContext.getDbType() == DbType.Postgres) {
            ConstraintExpression relationalConstraints = new BinaryExpression("srcid", BinaryOperator.Equal, id);
            return getEdges(constraints, relationalConstraints, step);
        } else {
            ConstraintExpression rootConstraint = new BinaryExpression("id", BinaryOperator.Equal, id);
            return getEdgesCypher(constraints, rootConstraint, step, false);
        }
    }

    private LinkedList<EventEdge> getEdgesCypher(BackTrackConstraints constraints, ConstraintExpression rootConstraints, int step, boolean incoming) {
        ConstraintExpression edgeConstraints = constraints.getEdgeConstraints();
        ConstraintExpression nodeConstraints = constraints.getNodeConstraints();
        if (nodeConstraints != null) {
            rootConstraints = new AndExpression(rootConstraints, nodeConstraints);
        }
        HashMap<String, Object> params = new HashMap<>();
        String cypher;
        if (incoming) {
            cypher = String.format("MATCH (sn)-[r]->(en) WHERE (%s) AND (%s) AND (%s) RETURN DISTINCT sn, r, en",
                    edgeConstraints == null ? "true" : edgeConstraints.toCypher("r", params),
                    nodeConstraints == null ? "true" : nodeConstraints.toCypher("sn", params),
                    rootConstraints.toCypher("en", params));
        } else {
            cypher = String.format("MATCH (sn)-[r]->(en) WHERE (%s) AND (%s) AND (%s) RETURN DISTINCT sn, r, en",
                    edgeConstraints == null ? "true" : edgeConstraints.toCypher("r", params),
                    rootConstraints.toCypher("sn", params),
                    nodeConstraints == null ? "true" : nodeConstraints.toCypher("en", params));
        }
//        System.out.println(">>>>>>>>>Cypher query:");
//        System.out.println(cypher);
//        System.out.println(">>>>>>>>>Params:");
//        System.out.println(params);

        LinkedList<EventEdge> edges = new LinkedList<>();

        try (Transaction tran = graphDb.beginTx()) {
            // run query
//            System.out.println(">>>>>>>>>Running query...");
            Result result = tran.execute(cypher, params);
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                EntityNode source = createNode((Node) row.get("sn"));
                EntityNode sink = createNode((Node) row.get("en"));
                EventEdge edge = createEdge(source, sink, (Relationship) row.get("r"));
                edges.add(edge);
                steps.put(edge.getID(), step + 1);
            }
//            System.out.printf(">>>>>>>>>>Output: %s lines\n", count);
        }
        return edges;
    }

    private LinkedList<EventEdge> getEdges(BackTrackConstraints constraints, ConstraintExpression relationalConstraints, int step) throws SQLException {
        ConstraintExpression edgeConstraints = constraints.getEdgeConstraints();
        if (edgeConstraints != null) {
            relationalConstraints = new AndExpression(relationalConstraints, edgeConstraints);
        }
        LinkedList<String> params = new LinkedList<>();
        String sql = String.join("\n",
                "WITH alledges AS (",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM fileevent UNION",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM networkevent UNION",
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, 0 AS amount FROM processevent )",
                String.format("SELECT * FROM alledges e WHERE %s ;", relationalConstraints.toSQL(params))
        );
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        int pos = 1;
        for (String param : params) {
            sqlStmt.setString(pos++, param);
        }

        ResultSet results = sqlStmt.executeQuery();
        LinkedList<EventEdge> edges = new LinkedList<>();
        // Store non-local vertices to be queried in nodeMap
        HashMap<Long, EntityNode> nodeMap = new HashMap<>();
        while (results.next()) {
            long id = results.getLong(1);
            long srcId = results.getLong(2);
            long dstId = results.getLong(3);
            BigDecimal startTime = results.getBigDecimal(4);
            BigDecimal endTime = results.getBigDecimal(5);
            String hostName = results.getString(6);
            String opType = results.getString(7);
            long amount = results.getLong(8);

            EntityNode source, sink;
            if (nodes.containsKey(srcId)) {
                source = nodes.get(srcId);
            } else if (nodeMap.containsKey(srcId)) {
                source = nodeMap.get(srcId);
            } else {
                source = new EntityNode(srcId);
                nodeMap.put(srcId, source);
            }
            if (nodes.containsKey(dstId)) {
                sink = nodes.get(dstId);
            } else if (nodeMap.containsKey(dstId)) {
                sink = nodeMap.get(dstId);
            } else {
                sink = new EntityNode(dstId);
                nodeMap.put(dstId, sink);
            }

            EventEdge edge = new EventEdge(source, sink, id, opType, amount, startTime, endTime, hostName);
            edges.add(edge);
            steps.put(edge.getID(), step + 1);
        }

        LinkedList<EventEdge> filtered;
        if (nodeMap.size() > 0) {
            getVertices(constraints.getNodeConstraints(), nodeMap);
            filtered = new LinkedList<>();
            for (EventEdge edge : edges) {
                long srcId = edge.getSource().getID();
                long dstId = edge.getSink().getID();
                if (nodes.containsKey(srcId) && nodes.containsKey(dstId)) {
                    filtered.add(edge);
                }
            }
        } else {
            filtered = edges;
        }
        return filtered;
    }

    private Collection<EntityNode> getStartVertices(BackTrackConstraints constraints) throws SQLException {
        HashMap<Long, EntityNode> map = new HashMap<>();
        ConstraintExpression ce = constraints.getPOIConstraints();
        if (constraints.getNodeConstraints() != null) {
            ce = new AndExpression(ce, constraints.getNodeConstraints());
        }
        if (ExecutionContext.getDbType() == DbType.Postgres) {
            getVertices(ce, map);
        } else {
            getVerticesCypher(ce, map);
        }
        return map.values();
    }

    private void getVerticesCypher(ConstraintExpression nodeConstraints, HashMap<Long, EntityNode> nodeMap) {
        // If querying by ids instead of POI constraints, wrap constraints with InExpression
        if (nodeMap.size() > 0) {
            ConstraintExpression ie = new InExpression(nodeMap.keySet());
            if (nodeConstraints != null) {
                nodeConstraints = new AndExpression(new InExpression(nodeMap.keySet()), nodeConstraints);
            } else {
                nodeConstraints = ie;
            }
        }
        HashMap<String, Object> params = new HashMap<>();
        String cypher = String.format("MATCH (n) WHERE %s RETURN DISTINCT n", nodeConstraints.toCypher("n", params));
//        System.out.println(">>>>>>>>>Cypher query:");
//        System.out.println(cypher);
//        System.out.println(">>>>>>>>>Params:");
//        System.out.println(params);

        try (Transaction tran = graphDb.beginTx()) {
            // run query
//            System.out.println(">>>>>>>>>Running query...");
            Result result = tran.execute(cypher, params);
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                EntityNode node = createNode((Node) row.get("n"));
                nodeMap.put(node.getID(), node);
            }
//            System.out.printf(">>>>>>>>>>Output: %s lines\n", count);
        }
    }

    private void getVertices(ConstraintExpression nodeConstraints, HashMap<Long, EntityNode> nodeMap) throws SQLException {
        // If querying by ids instead of POI constraints, wrap constraints with InExpression
        if (nodeMap.size() > 0) {
            ConstraintExpression ie = new InExpression(nodeMap.keySet());
            if (nodeConstraints != null) {
                nodeConstraints = new AndExpression(new InExpression(nodeMap.keySet()), nodeConstraints);
            } else {
                nodeConstraints = ie;
            }
        }
        LinkedList<String> params = new LinkedList<>();
        String sql = String.join("\n",
                "WITH allnodes (type, id, name, path, dstip, dstport, srcip, srcport, pid, exename, exepath, cmdline) AS (",
                "SELECT 'file', id, name, path, NULL::text, NULL::int, NULL::text, NULL::int, NULL::int, NULL::text, NULL::text, NULL::text FROM file UNION",
                "SELECT 'network', id, NULL::text, NULL::text, dstip::text, dstport, srcip::text, srcport, NULL::int, NULL::text, NULL::text, NULL::text FROM network UNION",
                "SELECT 'process', id, NULL::text, NULL::text, NULL::text, NULL::int, NULL::text, NULL::int, pid, exename, exepath, cmdline FROM process)",
                String.format("SELECT id FROM allnodes WHERE %s ;", nodeConstraints.toSQL(params))
        );
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        int pos = 1;
        for (String param : params) {
            sqlStmt.setString(pos++, param);
        }
//        System.out.println("SQL: " + sql);
//        System.out.println("PARAMS: " + params);
        ResultSet results = sqlStmt.executeQuery();
        if (nodeMap.size() > 0) {
            // Filter if query by id
            HashSet<Long> ids = new HashSet<>(nodeMap.keySet());
            while (results.next()) {
                long id = results.getLong(1);
                ids.remove(id);
            }
            for (long id : ids) {
                nodeMap.remove(id);
            }
        } else {
            while (results.next()) {
                long id = results.getLong(1);
                if (!nodes.containsKey(id) && !nodeMap.containsKey(id)) {
                    EntityNode node = new EntityNode(id);
                    nodeMap.put(id, node);
                }
            }
        }
        if (nodeMap.size() > 0) {
            extractFileNodes(nodeMap);
            extractNetworkNodes(nodeMap);
            extractProcessNodes(nodeMap);
            for (EntityNode node : nodeMap.values()) {
                nodes.put(node.getID(), node);
            }
        }
    }

    private void extractFileNodes(HashMap<Long, EntityNode> nodeMap) throws SQLException {
        String sql = "SELECT * FROM file WHERE id = any(?);";
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        Array ids = conn.createArrayOf("integer", nodeMap.keySet().toArray());
        sqlStmt.setArray(1, ids);
        ResultSet results = sqlStmt.executeQuery();
        while (results.next()) {
            long id = results.getLong(1);
            String ownerGroupId = results.getString(2);
            String hostname = results.getString(3);
            String name = results.getString(4);
            String ownerUserId = results.getString(5);
            String path = results.getString(6);

            FileEntity file = new FileEntity(ownerUserId, ownerGroupId, path, id, hostname, name);
            nodeMap.get(id).setF(file);
        }
    }

    private void extractNetworkNodes(HashMap<Long, EntityNode> nodeMap) throws SQLException {
        String sql = "SELECT id, dstip::text, dstport::text, hostname, srcip::text, srcport::text FROM network WHERE id = any(?);";
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        Array ids = conn.createArrayOf("integer", nodeMap.keySet().toArray());
        sqlStmt.setArray(1, ids);
        ResultSet results = sqlStmt.executeQuery();
        while (results.next()) {
            long id = results.getLong(1);
            String dstIp = results.getString(2);
            String dstPort = results.getString(3);
            String hostname = results.getString(4);
            String srcIp = results.getString(5);
            String srcPort = results.getString(6);

            NetworkEntity network = new NetworkEntity(srcIp, dstIp, srcPort, dstPort, id, hostname);
            nodeMap.get(id).setN(network);
        }
    }

    private void extractProcessNodes(HashMap<Long, EntityNode> nodeMap) throws SQLException {
        String sql = "SELECT * FROM process WHERE id = any(?);";
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        Array ids = conn.createArrayOf("int", nodeMap.keySet().toArray());
        sqlStmt.setArray(1, ids);
        ResultSet results = sqlStmt.executeQuery();
        while (results.next()) {
            long id = results.getLong(1);
            String exeName = results.getString(2);
            String exePath = results.getString(3);
            String hostname = results.getString(5);
            int pid = results.getInt(6);
            String ownerUserId = results.getString(7);
            String cmdLine = results.getString(8);

            ProcessEntity process = new ProcessEntity(hostname, Integer.toString(pid), ownerUserId, id, exePath, exeName);
            process.setCmdLine(cmdLine);
            nodeMap.get(id).setP(process);
        }
    }
}
