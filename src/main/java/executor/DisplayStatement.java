package executor;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;
import query.parser.GraphQuery;

public class DisplayStatement implements QueryStatement {
    private final ExecutionContext ctx;
    private final GraphQuery gq;

    public DisplayStatement(ExecutionContext exeCtx, GraphQuery graphQuery) {
        ctx = exeCtx;
        gq = graphQuery;
    }

    @Override
    public void execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> graph = gq.execute();
        ctx.display(graph);
    }

    @Override
    public String toString() {
        return "display " + gq + ";";
    }
}
