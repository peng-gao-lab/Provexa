package executor;

import org.jgrapht.graph.DirectedPseudograph;

import datamodel.EntityNode;
import datamodel.EventEdge;
import query.search.Search;
import query.search.SearchConstraints;
import query.search.SearchLocal;
import query.search.SearchRemoteOptimized;
import query.search.SearchRemoteRaw;

public class SearchStatement implements QueryStatement {
    private final ExecutionContext ctx;
    private SearchConstraints constraints;

    public SearchStatement(ExecutionContext exeCtx, String returnName, SearchConstraints constraints) {
        this.ctx = exeCtx;
        this.constraints = constraints;
        this.constraints.setReturnName(returnName, false); // false = not display-only
    }

    public SearchStatement(ExecutionContext exeCxt, SearchConstraints constraints) {
        this.ctx = exeCxt;
        this.constraints = constraints;
    }

    public SearchStatement(ExecutionContext exeCxt) {
        this.ctx = exeCxt;
    }

    @Override
    public void execute() throws Exception {
        DirectedPseudograph<EntityNode, EventEdge> graph;
        long startTime = System.currentTimeMillis();
        // If remote
        if (this.constraints.getRemote()) {
            if (this.ctx.isUseRecursive()) {
                this.ctx.log("Executing Remote Recursive Search... ");
                Search searchRemoteRaw = new SearchRemoteRaw(this.ctx.getConnection());
                graph = searchRemoteRaw.search(constraints);
            } else {
                this.ctx.log("Executing Remote Optimized Search... ");
                Search searchRemoteOptimized = new SearchRemoteOptimized(this.ctx.getConnection());
                graph = searchRemoteOptimized.search(constraints);
            }
        } else {
            // Local
            this.ctx.log("Executing Local Search... ");
            String graphID = constraints.getSource();
            DirectedPseudograph<EntityNode, EventEdge> sourceGraph = this.ctx.getGraph(graphID);
            if (sourceGraph == null) {
                System.out.println(String.format("The source %s does not exist", graphID));
                return;
            }
            Search searchLocal = new SearchLocal(sourceGraph);
            graph = searchLocal.search(constraints);
        }
        long endTime = System.currentTimeMillis();
        // Based on result
        if (graph != null) {
            if (constraints.getReturnName() != null) {
                // If assign to a variable
                System.out.println("Saving: " + constraints.getReturnName());
                ctx.setGraph(constraints.getReturnName(), graph);
            } else {
                // Otherwise, the graph will be displayed
                ctx.display(graph);
            }
            this.ctx.log("=============== Result ===============");
            this.ctx.log("Finished Query" + " in " + (endTime - startTime) + "ms");
            this.ctx.log("--> #vertices: " + graph.vertexSet().size() + ", #edges: " + graph.edgeSet().size());
        } else {
            this.ctx.log("The Result is NULL :(");
        }
    }

    @Override
    public String toString() {
        return  " Searching ";
    }
}
