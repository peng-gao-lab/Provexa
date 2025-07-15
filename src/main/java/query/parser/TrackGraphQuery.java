package query.parser;

import query.backtracking.BackTrack;
import query.backtracking.BackTrackConstraints;
import query.backtracking.BackTrackRemote;
import query.backtracking.BackTrackRemoteRecursive;
import datamodel.EntityNode;
import datamodel.EventEdge;
import executor.ExecutionContext;
import org.jgrapht.graph.DirectedPseudograph;

public class TrackGraphQuery implements GraphQuery {
    private static int idx = 0;
    private final ExecutionContext ctx;
    private final boolean isBackward;
    private final BackTrack bt;
    private final BackTrackConstraints bc;

    public TrackGraphQuery(ExecutionContext ctx, boolean isBackward, BackTrack bt, BackTrackConstraints bc) {
        this.ctx = ctx;
        this.isBackward = isBackward;
        this.bt = bt;
        this.bc = bc;
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> execute() throws Exception {
        if (bt.getClass() == BackTrackRemote.class) {
            ((BackTrackRemote) bt).conn = ctx.getConnection();
        } else if (bt.getClass() == BackTrackRemoteRecursive.class) {
            ((BackTrackRemoteRecursive) bt).conn = ctx.getConnection();
        }
        bt.setup();
        bc.setup(this.ctx);
        int i = idx++;
        long startTime = System.currentTimeMillis();
        DirectedPseudograph<EntityNode, EventEdge> result;
        if (isBackward) {
            ctx.log("Executing Backward Tracking...");
            result = bt.backTrackPOIEvent(bc);
        } else {
            ctx.log("Executing Forward Tracking...");
            result = bt.forwardTrackPOIEvent(bc);
        }
        long endTime = System.currentTimeMillis();
        if (isBackward) {
            ctx.log("=============== Result ===============");
            ctx.log("Finished Query" + " in " + (endTime - startTime) + "ms");
            ctx.log("--> #vertices: " + result.vertexSet().size() + ", #edges: " + result.edgeSet().size());
        } else {
            ctx.log("=============== Result ===============");
            ctx.log("Finished Query" + " in " + (endTime - startTime) + "ms");
            ctx.log("--> #vertices: " + result.vertexSet().size() + ", #edges: " + result.edgeSet().size());
        }
//        if (bt.getClass() == BackTrackRemote.class) {
//            GraphUtils exporter = new GraphUtils(result);
//            exporter.exportGraphDot("graph_remote");
//        } else if (bt.getClass() == BackTrackRemoteRecursive.class) {
//            GraphUtils exporter = new GraphUtils(result);
//            exporter.exportGraphDot("graph_remote_recursive");
//        }
        return result;
    }
}
