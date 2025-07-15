package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;
import executor.ExecutionContext;
import org.jgrapht.graph.DirectedPseudograph;

public class VariableGraphQuery implements GraphQuery {
    private final ExecutionContext ctx;
    private final String id;

    public VariableGraphQuery(ExecutionContext ctx, String id) {
        this.ctx = ctx;
        this.id = id;
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> result = ctx.getGraph(id);
        if (result == null) {
            throw new RuntimeException("Variable not defined: " + id);
        }
        return result;
    }

    @Override
    public String toString() {
        return id;
    }
}
