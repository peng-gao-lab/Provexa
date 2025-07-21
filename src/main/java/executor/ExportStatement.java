package executor;

import graph.GraphUtils;
import query.parser.GraphQuery;

public class ExportStatement implements QueryStatement {
    private final ExecutionContext cxt;
    private final GraphQuery gq;
    private final String fileName;

    public ExportStatement(ExecutionContext exeCtx, GraphQuery graphQuery, String as) {
        cxt = exeCtx;
        gq = graphQuery;
        fileName = as;
    }

    @Override
    public void execute() throws Exception {
        GraphUtils utils = new GraphUtils(gq.execute());
        utils.exportGraphDot(fileName);
    }

    @Override
    public String toString() {
        return "export " + gq + " as '" + fileName + "';";
    }
}
