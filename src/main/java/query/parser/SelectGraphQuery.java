package query.parser;

import query.backtracking.BackTrackConstraints;
import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;

public class SelectGraphQuery implements GraphQuery {
    private final GraphQuery gq;
    private final BackTrackConstraints bc;

    public SelectGraphQuery(GraphQuery graphQuery, BackTrackConstraints constraints) {
        gq = graphQuery;
        bc = constraints;
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> graph = gq.execute();
        DirectedPseudograph<EntityNode, EventEdge> result = new DirectedPseudograph<>(EventEdge.class);
        ConstraintExpression nodeConstraints = bc.getNodeConstraints();
        for (EntityNode n : graph.vertexSet()) {
            if (nodeConstraints == null || nodeConstraints.test(n)) {
                result.addVertex(n);
            }
        }
        ConstraintExpression edgeConstraints = bc.getEdgeConstraints();
        for (EventEdge e : graph.edgeSet()) {
            if (!result.containsVertex(e.getSource()) || !result.containsVertex(e.getSink())) continue;
            if (edgeConstraints == null || edgeConstraints.test(e)) {
                result.addEdge(e.getSource(), e.getSink(), e);
            }
        }
        return result;
    }
}
