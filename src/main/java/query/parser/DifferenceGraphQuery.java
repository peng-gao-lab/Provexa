package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;

public class DifferenceGraphQuery implements GraphQuery {
    private final GraphQuery left;
    private final GraphQuery right;

    public DifferenceGraphQuery(GraphQuery l, GraphQuery r) {
        left = l;
        right = r;
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> gl = left.execute();
        DirectedPseudograph<EntityNode, EventEdge> gr = right.execute();

        DirectedPseudograph<EntityNode, EventEdge> result = new DirectedPseudograph<>(EventEdge.class);
        for (EntityNode n : gl.vertexSet()) result.addVertex(n);
        for (EventEdge e : gl.edgeSet()) result.addEdge(e.getSource(), e.getSink(), e);
        result.removeAllVertices(gr.vertexSet());
        return result;
    }

    @Override
    public String toString() {
        return "(" + left + ") - (" + right + ")";
    }
}

