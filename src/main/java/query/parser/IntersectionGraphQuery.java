package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.HashSet;

public class IntersectionGraphQuery implements GraphQuery {
    private final GraphQuery left;
    private final GraphQuery right;

    public IntersectionGraphQuery(GraphQuery l, GraphQuery r) {
        left = l;
        right = r;
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> gl = left.execute();
        DirectedPseudograph<EntityNode, EventEdge> gr = right.execute();

        HashSet<EntityNode> nodes = new HashSet<>(gl.vertexSet());
        nodes.retainAll(gr.vertexSet());
        DirectedPseudograph<EntityNode, EventEdge> result = new DirectedPseudograph<>(EventEdge.class);
        for (EntityNode n : nodes) result.addVertex(n);
        for (EventEdge e : gl.edgeSet()) {
            if (nodes.contains(e.getSource()) && nodes.contains(e.getSink())) {
                result.addEdge(e.getSource(), e.getSink(), e);
            }
        }
        for (EventEdge e : gr.edgeSet()) {
            if (nodes.contains(e.getSource()) && nodes.contains(e.getSink())) {
                result.addEdge(e.getSource(), e.getSink(), e);
            }
        }
        return result;
    }


    @Override
    public String toString() {
        return "(" + left + ") & (" + right + ")";
    }
}

