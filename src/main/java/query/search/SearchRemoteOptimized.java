package query.search;

import datamodel.EntityNode;
import datamodel.EventEdge;
import db.Neo4jUtil;
import db.PostgresUtil;
import executor.ExecutionContext;
import org.jgrapht.graph.DirectedPseudograph;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.graphdb.*;
import query.parser.ConstraintExpression;
import query.parser.InExpression;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import static query.Utils.*;

public class SearchRemoteOptimized implements Search {
    public Connection conn;
    private HashMap<Integer, EntityNode> nodeMap = new HashMap<>();
    private HashMap<String, HashSet<Long>> entityMap = new HashMap<>();

    public SearchRemoteOptimized() {}

    public SearchRemoteOptimized(Connection conn) {
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
        long sTime = System.currentTimeMillis();
        // Match edges
        LinkedList<LinkedList<LinkedList<EventEdge>>> matchedEdges = matchEdges(constraints);
        // Deal with the matched Edge
        LinkedList<LinkedList<EventEdge>> allPaths = processMatchedEdge(constraints, matchedEdges);
        // Parse the result
        ArrayList<EventEdge> graphEdges = new ArrayList<>();
        HashMap<Integer, EntityNode> graphNodes = new HashMap<>();
        parseResult(graphEdges, graphNodes, allPaths);
        // Build a graph
        DirectedPseudograph<EntityNode, EventEdge> graph = buildGraph(graphEdges, graphNodes);
        long eTime = System.currentTimeMillis();
        System.out.println("Finished search" + " in " + (eTime - sTime) + "ms");
        return graph;
    }

    public LinkedList<LinkedList<LinkedList<EventEdge>>> matchEdges(SearchConstraints constraints) {
        LinkedList<LinkedList<LinkedList<EventEdge>>> matchedEdges = new LinkedList<>();
        ArrayList<ArrayList<String>> relations = constraints.getEdges();
        HashMap<String, ConstraintExpression> nodeConst = constraints.getNodeConstraints();
        ConstraintExpression sourceConst, sinkConst;
//        System.out.println(nodeConst);
        for (ArrayList<String> relation : relations) {
            // Initiate param lists
            LinkedList<String> nodeParams1 = new LinkedList<>();
            LinkedList<String> nodeParams2 = new LinkedList<>();
            LinkedList<String> opTypeParam = new LinkedList<>();
            String searchSql = getAllNodeSQL();
            searchSql += getAllEdgeSQL();
            // Get relation
            String source = relation.get(0);
            String opt = relation.get(1);
            String sink = relation.get(2);
            // Get node constraints
            if (this.entityMap.containsKey(source)) {
                sourceConst = new InExpression(this.entityMap.get(source));
            } else {
                sourceConst = nodeConst.get(source);
            }
            if (this.entityMap.containsKey(sink)) {
                sinkConst = new InExpression(this.entityMap.get(sink));
            } else {
                sinkConst = nodeConst.get(sink);
            }
            // Set table names
            sourceConst.setSqlTable("allnodes");
            sinkConst.setSqlTable("allnodes");
            // Get sql string
            String nodesql1 = sourceConst.toSQL(nodeParams1);
            String nodesql2 = sinkConst.toSQL(nodeParams2);
            searchSql += String.format(getNodeSQL("node1"), nodesql1);
            searchSql += String.format(getNodeSQL("node2"), nodesql2);
            String optsql;
            if (opt.equals("null")) {
                optsql = "? = ?";
                opTypeParam.add("true");
                opTypeParam.add("true");
            } else {
                optsql = "alledges.optype = ?";
                opTypeParam.add(opt);
            }
            searchSql += String.format(getEdgeSQL(), optsql);
            searchSql += getEvent("node1", "node2");
            searchSql += getResult();
            // Print composed sql query
//            System.out.printf("--> Optimized SQL query: (length %d)%n", searchSql.length());
//            System.out.println(searchSql);
//            System.out.println("---");
//            System.out.println(nodeParams1);
//            System.out.println(nodeParams2);
//            System.out.println(opTypeParam);
            // Run the SQL query
            LinkedList<LinkedList<EventEdge>> matchedEdge = runSQL(source, sink,
                    searchSql, nodeParams1, nodeParams2, opTypeParam);
            if (matchedEdge==null) return null;
            matchedEdges.add(matchedEdge);
        }
        return matchedEdges;
    }

    public LinkedList<LinkedList<EventEdge>> processMatchedEdge(SearchConstraints constraints,
                                                                LinkedList<LinkedList<LinkedList<EventEdge>>> matchedEdges) {
        ArrayList<List<String>> optsBak = constraints.getEdgeConstraints();
        ArrayList<List<String>> opts = (ArrayList<List<String>>) optsBak.clone();
        LinkedList<LinkedList<EventEdge>> allPaths = new LinkedList<>();
        while (matchedEdges.size() > 1) {
            // Find the smallest join
            int idx = 0;
            int maxValue = Integer.MAX_VALUE;
            for (int i=0; i<matchedEdges.size()-1; i++) {
                LinkedList<LinkedList<EventEdge>> prePath = matchedEdges.get(i);
                LinkedList<LinkedList<EventEdge>> postPath = matchedEdges.get(i+1);
                if (prePath.size() * postPath.size() < maxValue) {
                    idx = i;
                }
            }
            List<String> opt = opts.get(idx);
            LinkedList<LinkedList<EventEdge>> newPath = combineTwoPaths(matchedEdges.get(idx), matchedEdges.get(idx+1), opt);
            // Replace original two paths with one combined path
            // Remove original paths
            matchedEdges.remove(idx);
            matchedEdges.remove(idx);
            opts.remove(idx);
            // Add new path
            matchedEdges.add(idx, newPath);
        }
        for (LinkedList<LinkedList<EventEdge>> edges : matchedEdges) {
            allPaths.addAll(edges);
        }
        return allPaths;
    }

    public LinkedList<LinkedList<EventEdge>> combineTwoPaths(LinkedList<LinkedList<EventEdge>> path1,
                                                             LinkedList<LinkedList<EventEdge>> path2,
                                                             List<String> opt) {
        LinkedList<LinkedList<EventEdge>> combinedEdge = new LinkedList<>();
        double time = getInterval(opt);
        try {
            LinkedList<EventEdge> prePath = path1.removeFirst();
            while (prePath!=null) {
                EventEdge lastEdge = prePath.getLast();
                for (LinkedList<EventEdge> postPath : path2) {
                    EventEdge edge = postPath.getFirst();
                    if (edge.getStartTime().subtract(lastEdge.getEndTime()).doubleValue() < time) {
                        LinkedList<EventEdge> newPath = new LinkedList<>();
                        newPath.addAll(prePath);
                        newPath.addAll(postPath);
                        combinedEdge.addLast(newPath);
                    }
                }
                prePath = path1.removeFirst();
            }
        } catch (NoSuchElementException exp){
//            System.out.println("Path finished");
        }
        return combinedEdge;
    }

    public void parseResult(ArrayList<EventEdge> graphEdges, HashMap<Integer, EntityNode> graphNodes,
                            LinkedList<LinkedList<EventEdge>> allPaths) {
        LinkedList<EventEdge> path = allPaths.pollLast();
        int srcId, dstId;
        while (path != null) {
            EventEdge edge = path.pollLast();
            while (edge != null) {
                if (!graphEdges.contains(edge)) {
                    graphEdges.add(edge);
                    srcId = (int) edge.getSource().getID();
                    dstId = (int) edge.getSink().getID();
                    if (!graphNodes.containsKey(srcId)) {
                        graphNodes.put(srcId, this.nodeMap.get(srcId));
                    }
                    if (!graphNodes.containsKey(dstId)) {
                        graphNodes.put(dstId, this.nodeMap.get(dstId));
                    }
                }
                edge = path.pollLast();
            }
            path = allPaths.pollLast();
        }
    }

    public DirectedPseudograph<EntityNode, EventEdge> buildGraph(ArrayList<EventEdge> graphEdges, HashMap<Integer, EntityNode> graphNodes) {
        try {
            // Build the graph
            extractFileNodes(this.conn, graphNodes);
            extractNetworkNodes(this.conn, graphNodes);
            extractProcessNodes(this.conn, graphNodes);

            DirectedPseudograph<EntityNode, EventEdge> graph = new DirectedPseudograph<>(EventEdge.class);
            for (EntityNode node : graphNodes.values()) {
                graph.addVertex(node);
            }
            for (EventEdge edge : graphEdges) {
                graph.addEdge(edge.getSource(), edge.getSink(), edge);
            }
            return graph;
        } catch (SQLTimeoutException e) {
            return new DirectedPseudograph<>(EventEdge.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LinkedList<LinkedList<EventEdge>> runSQL(String sourceID,
                                                    String sinkID,
                                                    String sql,
                                                    LinkedList<String> nodeParams1,
                                                    LinkedList<String> nodeParams2,
                                                    LinkedList<String> opTypeParam) {
        LinkedList<LinkedList<EventEdge>> matchedEdge = new LinkedList<>();
        try {
            // Initiate the SQL query
            PreparedStatement edgeStmt = conn.prepareStatement(sql);
            edgeStmt.setQueryTimeout(1800);
            HashSet<Long> sourceSet = new HashSet<>();
            HashSet<Long> sinkSet = new HashSet();
            // Fill in the placeholder
            int pos = 1;
            for (String param : nodeParams1) {
                edgeStmt.setString(pos++, param);
            }
            for (String param : nodeParams2) {
                edgeStmt.setString(pos++, param);
            }
            for (String param : opTypeParam) {
                edgeStmt.setString(pos++, param);
            }
            edgeStmt.execute();
            // Process the results
            ResultSet edgeResults = edgeStmt.getResultSet();
            while (edgeResults.next()) {
                long id = edgeResults.getLong(1);
                int srcId = edgeResults.getInt(2);
                int dstId = edgeResults.getInt(3);
                BigDecimal startTime = edgeResults.getBigDecimal(4);
                BigDecimal endTime = edgeResults.getBigDecimal(5);
                String hostName = edgeResults.getString(6);
                String opType = edgeResults.getString(7);
                long amount = edgeResults.getLong(8);

                // Buffer ids
                sourceSet.add(Integer.toUnsignedLong(srcId));
                sinkSet.add(Integer.toUnsignedLong(dstId));

                EntityNode source, sink;
                if (this.nodeMap.containsKey(srcId)) {
                    source = this.nodeMap.get(srcId);
                } else {
                    source = new EntityNode(srcId);
                    this.nodeMap.put(srcId, source);
                }
                if (this.nodeMap.containsKey(dstId)) {
                    sink = this.nodeMap.get(dstId);
                } else {
                    sink = new EntityNode(dstId);
                    this.nodeMap.put(dstId, sink);
                }
                EventEdge edge = new EventEdge(source, sink, id, opType, amount, startTime, endTime, hostName);
                LinkedList<EventEdge> tmp = new LinkedList<>();
                tmp.add(edge);
                matchedEdge.add(tmp);
            }
            // buffer ids
            if (this.entityMap.containsKey(sourceID)) {
                this.entityMap.remove(sourceID, sourceSet);
            } else {
                this.entityMap.put(sourceID, sourceSet);
            }
            if (this.entityMap.containsKey(sinkID)) {
                this.entityMap.remove(sinkID, sinkSet);
            } else {
                this.entityMap.put(sinkID, sinkSet);
            }
            return matchedEdge;

        } catch (SQLTimeoutException e) {
            System.out.println("Timeout exception in optimized search");
            System.out.println("Finished search" + " in " + "-1ms");
            return new LinkedList<>();
        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
            System.out.println("Other exceptions in optimized search");
            System.out.println("Finished search" + " in " + "-1ms");
            return new LinkedList<>();
        }
    }

    public String getAllNodeSQL() {
        String query="allnodes (type, id, name, path, dstip, dstport, srcip, srcport, pid, exename, exepath, cmdline) AS (\n" +
                "SELECT 'file', id, name, path, NULL::text, NULL::int, NULL::text, NULL::int, NULL::int, NULL::text, NULL::text, NULL::text FROM file UNION\n" +
                "SELECT 'network', id, NULL::text, NULL::text, CAST (dstip AS text), dstport, CAST (srcip AS text), srcport, NULL::int, NULL::text, NULL::text, NULL::text FROM network UNION\n" +
                "SELECT 'process', id, NULL::text, NULL::text, NULL::text, NULL::int, NULL::text, NULL::int, pid, exename, exepath, cmdline FROM process),\n";
        return String.format("WITH %s", query);
    }

    public String getNodeSQL(String nodeID) {
        String query = nodeID + " AS (SELECT * FROM allnodes WHERE %s), \n";
        return query;
    }

    public String getAllEdgeSQL() {
        String query="alledges AS (\n" +
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM fileevent UNION\n" +
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM networkevent UNION\n" +
                "SELECT id, srcid, dstid, starttime, endtime, hostname, optype, 0 AS amount FROM processevent ),\n";
        return query;
    }

    public String getEdgeSQL(){
        String query = "edges AS (SELECT alledges.* FROM alledges WHERE %s),\n";
        return query;
    }

    // Search events based on constraints
    public String getEvent(String nodeID1, String nodeID2) {
        String query = "event (id, srcid, dstid, starttime, endtime, hostname, optype, amount) AS (\n" +
                "    SELECT edges.* FROM\n" +
                "    %s n1 INNER JOIN edges ON n1.id = edges.srcid\n" +
                "    INNER JOIN %s n2 ON edges.dstid = n2.id\n" +
                ")\n";
        return String.format(query, nodeID1, nodeID2);
    }

    public String getResult() {
        return "SELECT * FROM event;";
    }

    public DirectedPseudograph<EntityNode, EventEdge> searchCypher(SearchConstraints constraints) throws IOException {
        DirectedPseudograph<EntityNode, EventEdge> graph = new DirectedPseudograph<>(EventEdge.class);
        DatabaseManagementService managementService = Neo4jUtil.connectDb(Neo4jUtil.db);
        GraphDatabaseService graphDb = managementService.database(Neo4jUtil.db);
        long startTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> relations = constraints.getEdges();
        HashMap<String, ConstraintExpression> nodeConst = constraints.getNodeConstraints();
        ArrayList<List<String>> opts = constraints.getEdgeConstraints();
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
}
