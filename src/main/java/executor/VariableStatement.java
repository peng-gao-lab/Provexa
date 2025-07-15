package executor;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;
import query.parser.GraphQuery;

public class VariableStatement implements QueryStatement {
    private final ExecutionContext ctx;
    private String name;
    private Boolean isDisplay;

    public VariableStatement(ExecutionContext exeCtx, String name, Boolean isDisplay) {
        ctx = exeCtx;
        name = name;
        isDisplay = isDisplay;
    }

    @Override
    public void execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> graph = ctx.getGraph(name);
        ctx.display(graph);
    }

    @Override
    public String toString() {
        return "display " + name + ";";
    }
}
