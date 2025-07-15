package query.search;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;
import query.parser.ConstraintExpression;
import executor.ExecutionContext.DbType;
import query.parser.GraphQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchConstraints {
    private String source;
    private HashMap<String, ConstraintExpression> constraintMap;
    private HashMap<String, String> typeMap;
    private ArrayList<ArrayList<String>> relations;
    private ArrayList<List<String>> opts;
    private String resultName=null;
    private Boolean isDisplay=false;
    // For evaluation
    private boolean ignoreConstraints = false;
    // remote database or in-memory
    private boolean isRemote = false;
    // Db type
    private static DbType dbType = DbType.Postgres;
    // If populate neo4j database
    private static boolean ifPopulate = true;

    // Initiate
    public SearchConstraints(){
        // Neo4j or PostgreSQL
        setRemote(true);
        this.source = null;
    }
    public SearchConstraints(String source){
        if (source == null) {
            // Neo4j or PostgreSQL
            setRemote(true);
            this.source = null;
        } else {
            // Local
            setRemote(false);
            this.source = source;
        }
        this.typeMap = new HashMap<>();
    }
    // get source
    public String getSource() {
        return this.source;
    }

    // Node constraints
    public void setNodeConstraints(HashMap<String, ConstraintExpression> cm) {
        if (ignoreConstraints) return;
        this.constraintMap = cm;
    }
    public HashMap<String, ConstraintExpression> getNodeConstraints() {
        return this.constraintMap;
    }

    // Node type
    public void setNodeType(String name, String type) {
        if (ignoreConstraints) return;
        this.typeMap.put(name, type);
    }
    public HashMap<String, String> getNodeType() {
        return this.typeMap;
    }

    // Edges e.g., e1[read]->e2
    public void setEdges(ArrayList<ArrayList<String>> relations) {
        if (ignoreConstraints) return;
//        if (relations != null) relations.setSqlTable("e");
        this.relations = relations;
    }
    public ArrayList<ArrayList<String>> getEdges() {
        return this.relations;
    }

    // Edge constraints e.g., edges &&[<1s] edges
    public void setEdgeConstraints(ArrayList<List<String>> opts) {
        if (ignoreConstraints) return;
//        if (relations != null) constraints.setSqlTable("e");
        this.opts = opts;
    }
    public ArrayList<List<String>> getEdgeConstraints() {
        return this.opts;
    }

    // Set returned result name
    public void setReturnName(String name, Boolean isDisplay) {
        this.resultName = name;
        this.isDisplay = isDisplay;
    }
    public String getReturnName() {
        if (this.isDisplay) return null;
        else return this.resultName;
    }

    // whether ignore or not
    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
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

    // remote database
    public void setRemote(Boolean value) {isRemote = value;}
    public boolean getRemote() {return isRemote;}
}
