package query.backtracking;

import datamodel.EntityNode;
import datamodel.EventEdge;
import executor.ExecutionContext;
import executor.ExecutionContext.DbType;
import org.jgrapht.graph.DirectedPseudograph;
import query.parser.ConstraintExpression;
import query.parser.GraphQuery;
import query.parser.InExpression;

import java.util.HashMap;
import java.util.Set;

public class BackTrackConstraints {
    private ConstraintExpression POIConstraints;
    private String POIGraphID;
    private boolean isPOIConstraint;
    private Set<EntityNode> POINodes;
    private ConstraintExpression nodeConstraints;
    private ConstraintExpression edgeConstraints;
    private int timeConstraintSecs = -1;
    private int stepConstraint = -1;
    // For evaluation
    private boolean ignoreConstraints = false;

    private static DbType dbType = DbType.Postgres;
    // If populate neo4j database
    private static boolean ifPopulate = true;

    public BackTrackConstraints(ConstraintExpression poi) {
        this.POIConstraints = poi;
        this.isPOIConstraint = true;
    }

    public BackTrackConstraints() {
        this.POIConstraints = null;
        this.isPOIConstraint = false;
    }

    public BackTrackConstraints(String POIGraphID) {
        this.POIGraphID = POIGraphID;
        this.isPOIConstraint = false;
    }

    public ConstraintExpression getPOIConstraints() {
        if (this.isPOIConstraint) {
            return this.POIConstraints;
        }
        HashMap<Long, EntityNode> nodeMap = new HashMap<>();
        for (EntityNode n : POINodes) {
            if (nodeConstraints != null && !nodeConstraints.test(n)) {
                continue;
            }
            nodeMap.put(n.getID(), n);
        }
        this.POIConstraints = new InExpression(nodeMap.keySet());
        return this.POIConstraints;
    }

    public void setup(ExecutionContext exeCtx) {
        if (this.isPOIConstraint) return;
        DirectedPseudograph<EntityNode, EventEdge> graph = exeCtx.getGraph(this.POIGraphID);
        if (graph == null) {
            throw new RuntimeException("Variable not defined: " + this.POIGraphID);
        }
        POINodes = graph.vertexSet();
//        System.out.println("POINodes: " + POINodes);
    }

    public void setNodeConstraints(ConstraintExpression constraints) {
        if (ignoreConstraints) return;
        nodeConstraints = constraints;
    }

    public ConstraintExpression getNodeConstraints() {
        return nodeConstraints;
    }

    public void setEdgeConstraints(ConstraintExpression constraints) {
        if (ignoreConstraints) return;
        if (constraints != null) constraints.setSqlTable("e");
        edgeConstraints = constraints;
    }

    public ConstraintExpression getEdgeConstraints() {
        return edgeConstraints;
    }

    public int getTimeConstraintSecs() {
        return timeConstraintSecs;
    }

    public void setTimeConstraintSecs(int secs) {
        if (ignoreConstraints) return;
        timeConstraintSecs = secs;
    }

    public int getStepConstraint() {
        return stepConstraint;
    }

    public void setStepConstraint(int steps) {
        if (ignoreConstraints) return;
        stepConstraint = steps;
    }

    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
    }

}
