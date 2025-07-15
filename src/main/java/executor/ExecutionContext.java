package executor;

import datamodel.EntityNode;
import datamodel.EventEdge;
import db.Neo4jUtil;
import db.PostgresUtil;
import graph.GraphUtils;
import org.jgrapht.graph.DirectedPseudograph;
import query.backtracking.BackTrackConstraints;
import query.parser.ConstraintExpression;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExecutionContext {
    private final HashMap<String, DirectedPseudograph<EntityNode, EventEdge>> graphs;
    private Connection connection = null;
    private boolean isVerbose = false;
    private boolean useRecursive = false;
    private boolean ignoreConstraints = false;

    private boolean isWebContext = false;
    private StringBuilder webLog;
    private LinkedList<String> webGraphs;
    // Different databases
    public enum DbType {
        Neo4j, Postgres
    }
    private HashMap<String, ConstraintExpression> cm = new HashMap<>();

    private static DbType dbType;
    private static Boolean ifPopulate=false;

    public ExecutionContext() {
        graphs = new HashMap<>();
        webLog = new StringBuilder();
        webGraphs = new LinkedList<>();
    }

    public DirectedPseudograph<EntityNode, EventEdge> getGraph(String id) {
        if (graphs.get(id) == null) {
            System.out.println(String.format("The source %s does not exist :(", id));
            System.out.println(graphs.keySet());
        } else {
            System.out.println(String.format("The source %s exist :)", id));
        }
        return graphs.get(id);
    }

    public void setGraph(String id, DirectedPseudograph<EntityNode, EventEdge> graph) {
        graphs.put(id, graph);
    }

    public void removeGraph(String id) {
        graphs.remove(id);
    }

    public void log(String message) {
        System.out.println(message);
        if (isWebContext) {
            webLog.append(message);
            webLog.append('\n');
        }
    }

    public void display(DirectedPseudograph<EntityNode, EventEdge> graph) {
        if (isWebContext) {
            GraphUtils gu = new GraphUtils(graph);
            webGraphs.add(gu.exportGraphDotString());
        }
    }

    public WebResponse dumpResponse() {
        if (!isWebContext) return null;
        WebResponse response = new WebResponse(webLog.toString(), webGraphs);
        webLog = new StringBuilder();
        webGraphs = new LinkedList<>();
        return response;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(String dbName) throws SQLException, IOException {
        if (connection != null) connection.close();
        if (getDbType() == DbType.Postgres) {
            connection = PostgresUtil.getConnection(dbName);
        } else {
            Neo4jUtil.db = dbName.replaceAll("_", "-");
            log(String.format("Connected to Neo4j %s", dbName));
        }
        if (isVerbose && connection!=null) {
            String nodeQuery = "WITH nodes AS\n" +
                    "(SELECT id FROM file UNION ALL SELECT id FROM process UNION ALL SELECT id FROM network)\n" +
                    "SELECT COUNT(*) FROM nodes;";
            String edgeQuery = "WITH edges AS\n" +
                    "(SELECT id FROM fileevent UNION ALL SELECT id FROM processevent UNION ALL SELECT id FROM networkevent)\n" +
                    "SELECT COUNT(*) FROM edges;";
            PreparedStatement sqlStmt = connection.prepareStatement(nodeQuery);
            ResultSet results = sqlStmt.executeQuery();
            results.next();
            long nodeCount = results.getLong(1);
            sqlStmt = connection.prepareStatement(edgeQuery);
            results = sqlStmt.executeQuery();
            results.next();
            long edgeCount = results.getLong(1);
            log(String.format("Connected to %s with %d nodes and %d edges", dbName, nodeCount, edgeCount));
        }
    }

    public void setVerbose(boolean verbose) {
        isVerbose = verbose;
    }

    public boolean isUseRecursive() {
        return useRecursive;
    }

    public void setUseRecursive(boolean useRecursive) {
        this.useRecursive = useRecursive;
    }

    public boolean isIgnoreConstraints() {
        return ignoreConstraints;
    }

    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
    }

    public void enableWebContext() {
        isWebContext = true;
    }

    // Database related
    public static DbType getDbType() {
        return dbType;
    }
    public static void setDbType(DbType type) {
        dbType = type;
    }

    // If populate
    public static boolean getIfPopulate() {
        return ifPopulate;
    }
    public static void setIfPopulate(Boolean value) {
        ifPopulate = value;
    }
}
