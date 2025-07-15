package executor;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;
import query.parser.GraphQuery;

public class AssignStatement implements QueryStatement {
    private final ExecutionContext ctx;
    private final String target;
    private final GraphQuery gq;

    public AssignStatement(ExecutionContext exeCxt, String id, GraphQuery graphQuery) {
        ctx = exeCxt;
        target = id;
        gq = graphQuery;
    }

    @Override
    public void execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> graph = gq.execute();
        ctx.setGraph(target, graph);
    }

    @Override
    public String toString() {
        return target + " = " + gq + ";";
    }
}
