package query.backtracking;

import executor.ExecutionContext;
import org.jgrapht.graph.DirectedPseudograph;
import datamodel.*;
import query.parser.ConstraintExpression;
import query.parser.GraphQuery;

import java.math.BigDecimal;
import java.util.*;

public class BackTrackLocal implements BackTrack {
    DirectedPseudograph<EntityNode, EventEdge> originalGraph;
    GraphQuery gq;
    ExecutionContext exeCtx;
    String sourceGraphID;


    private static class EventEdgeComparator implements Comparator<EventEdge> {
        boolean isBackward;

        EventEdgeComparator(boolean isBackward) {
            this.isBackward = isBackward;
        }

        @Override
        public int compare(EventEdge o1, EventEdge o2) {
            if (isBackward) {
                return o2.endTime.compareTo(o1.endTime);
            } else {
                return o1.endTime.compareTo(o2.endTime);
            }
        }
    }

    public BackTrackLocal(ExecutionContext exeCtx, String sourceID){
        this.exeCtx = exeCtx;
        this.sourceGraphID = sourceID;
    }

    public BackTrackLocal(DirectedPseudograph<EntityNode, EventEdge> input){
        originalGraph = (DirectedPseudograph<EntityNode, EventEdge>) input.clone();
    }

    public BackTrackLocal(GraphQuery graphQuery) {
        gq = graphQuery;
    }

    @Override
    public void setup() throws Exception {
        if (this.gq != null && originalGraph == null) {
            this.originalGraph = this.gq.execute();
        }
        if (this.sourceGraphID != null && originalGraph == null) {
            DirectedPseudograph<EntityNode, EventEdge> input = exeCtx.getGraph(this.sourceGraphID);
            this.originalGraph = (DirectedPseudograph<EntityNode, EventEdge>) input.clone();
        }
    }

    public DirectedPseudograph<EntityNode, EventEdge> backTrackPOIEvent(BackTrackConstraints constraints){
        System.out.println("backTrackPOIEvent invoked in " + this.getClass().getName());
        long sTime = System.currentTimeMillis();
        DirectedPseudograph<EntityNode, EventEdge> backTrackGraph = new DirectedPseudograph<>(EventEdge.class);
        List<EntityNode> start = getStartVertices(constraints);
        Map<EntityNode, BigDecimal> timeThresholds = new HashMap<>();
        Set<EntityNode> nodeInTheQueue = new HashSet<>();
        PriorityQueue<EventEdge> pq = new PriorityQueue<>(new EventEdgeComparator(true));
        ConstraintExpression nodeConstraint = constraints.getNodeConstraints();
        ConstraintExpression edgeConstraint = constraints.getEdgeConstraints();
        long endTime = constraints.getTimeConstraintSecs();
        if (endTime > 0) endTime = System.currentTimeMillis() + endTime * 1000;
        for (EntityNode node : start) {
            if (nodeConstraint != null && !nodeConstraint.test(node)) continue;
            BigDecimal latestOpTime = getLatestOperationTime(node);
            timeThresholds.put(node, latestOpTime);
            nodeInTheQueue.add(node);
            backTrackGraph.addVertex(node);
            if (edgeConstraint == null) {
                pq.addAll(originalGraph.incomingEdgesOf(node));
            } else {
                for (EventEdge e : originalGraph.incomingEdgesOf(node)) {
                    if (edgeConstraint.test(e)) pq.add(e);
                }
            }
        }
        Set<EventEdge> nodesInStepRange = bfs(nodeInTheQueue, constraints, true);
        while (!pq.isEmpty()) {
            if (endTime > 0 && System.currentTimeMillis() > endTime) break;
            EventEdge edge = pq.poll();
            EntityNode sink = edge.getSink();
            BigDecimal sinkThreshold = timeThresholds.get(sink);
            if (edge.getStartTime().compareTo(sinkThreshold) > 0) continue;
            EntityNode source = edge.getSource();
            if (nodeConstraint != null && !nodeConstraint.test(source)) continue;
            if (!nodesInStepRange.contains(edge)) continue;
            if (!nodeInTheQueue.contains(source)) {
                nodeInTheQueue.add(source);
                backTrackGraph.addVertex(source);
                if (edgeConstraint == null) {
                    pq.addAll(originalGraph.incomingEdgesOf(source));
                } else {
                    for (EventEdge e : originalGraph.incomingEdgesOf(source)) {
                        if (edgeConstraint.test(e)) pq.add(e);
                    }
                }
                timeThresholds.put(source, BigDecimal.ZERO);
            }
            // sourceThreshold = MIN(edge.endtime, sinkThreshold)
            // Tighten the threshold of source node
            BigDecimal thresholdForSource =
                    edge.endTime.compareTo(sinkThreshold) < 0 ? edge.endTime : sinkThreshold;
            // If this node already exists in the queue,
            // then loose the threshold of source node to the maximum
            if(timeThresholds.get(source).compareTo(thresholdForSource) < 0){
                timeThresholds.put(source, thresholdForSource);
            }
            backTrackGraph.addEdge(source, sink, edge);
        }
        long eTime = System.currentTimeMillis();
        System.out.println("Finished tracking" + " in " + (eTime - sTime) + "ms");
        return backTrackGraph;
    }

    public DirectedPseudograph<EntityNode, EventEdge> forwardTrackPOIEvent(BackTrackConstraints constraints){
        System.out.println("forwardTrackPOIEvent invoked in " + this.getClass().getName());
        long sTime = System.currentTimeMillis();
        DirectedPseudograph<EntityNode, EventEdge> backTrackGraph = new DirectedPseudograph<>(EventEdge.class);
        List<EntityNode> start = getStartVertices(constraints);
        Map<EntityNode, BigDecimal> timeThresholds = new HashMap<>();
        Set<EntityNode> nodeInTheQueue = new HashSet<>();
        PriorityQueue<EventEdge> pq = new PriorityQueue<>(new EventEdgeComparator(false));
        ConstraintExpression nodeConstraint = constraints.getNodeConstraints();
        ConstraintExpression edgeConstraint = constraints.getEdgeConstraints();
        long endTime = constraints.getTimeConstraintSecs();
        if (endTime > 0) endTime = System.currentTimeMillis() + endTime * 1000;
        for (EntityNode node : start) {
            if (nodeConstraint != null && !nodeConstraint.test(node)) continue;
            BigDecimal firstOpTime = getFirstOperationTime(node);
            timeThresholds.put(node, firstOpTime);
            nodeInTheQueue.add(node);
            backTrackGraph.addVertex(node);
            if (edgeConstraint == null) {
                pq.addAll(originalGraph.outgoingEdgesOf(node));
            } else {
                for (EventEdge e : originalGraph.outgoingEdgesOf(node)) {
                    if (edgeConstraint.test(e)) pq.add(e);
                }
            }
        }
        Set<EventEdge> nodesInStepRange = bfs(nodeInTheQueue, constraints, false);
        while (!pq.isEmpty()) {
            if (endTime > 0 && System.currentTimeMillis() > endTime) break;
            EventEdge edge = pq.poll();
            EntityNode source = edge.getSource();
            BigDecimal sourceThreshold = timeThresholds.get(source);
            if (edge.getEndTime().compareTo(sourceThreshold) < 0) continue;
            EntityNode sink = edge.getSink();
            if (nodeConstraint != null && !nodeConstraint.test(sink)) continue;
            if (!nodesInStepRange.contains(edge)) continue;
            if (!nodeInTheQueue.contains(sink)) {
                nodeInTheQueue.add(sink);
                backTrackGraph.addVertex(sink);
                if (edgeConstraint == null) {
                    pq.addAll(originalGraph.outgoingEdgesOf(sink));
                } else {
                    for (EventEdge e : originalGraph.outgoingEdgesOf(sink)) {
                        if (edgeConstraint.test(e)) pq.add(e);
                    }
                }
                timeThresholds.put(sink, BigDecimal.ZERO);
            }
            BigDecimal thresholdForSink =
                    edge.startTime.compareTo(sourceThreshold) > 0 ? edge.startTime : sourceThreshold;
            if(timeThresholds.get(sink).compareTo(thresholdForSink) < 0){
                timeThresholds.put(sink, thresholdForSink);
            }
            backTrackGraph.addEdge(source, sink, edge);
        }
        long eTime = System.currentTimeMillis();
        System.out.println("Finished tracking" + " in " + (eTime - sTime) + "ms");
        return backTrackGraph;
    }

    private List<EntityNode> getStartVertices(BackTrackConstraints input) {
        ConstraintExpression expr = input.getPOIConstraints();
        ArrayList<EntityNode> result = new ArrayList<>();
        for (EntityNode n : originalGraph.vertexSet()) {
            if (expr.test(n)) {
                result.add(n);
            }
        }
        return result;
    }

    private BigDecimal getLatestOperationTime(EntityNode node) {
        // The largest end time of POI vertex
        assert node != null;
        Set<EventEdge> edges = originalGraph.incomingEdgesOf(node);
        BigDecimal res = BigDecimal.ZERO;
        for (EventEdge e : edges) {
            if (res.compareTo(e.getEndTime()) < 0) {
                res = e.getEndTime();
            }
        }
        return res;
    }

    private BigDecimal getFirstOperationTime(EntityNode node) {
        assert node != null;
        Set<EventEdge> edges = originalGraph.outgoingEdgesOf(node);
        BigDecimal res = null;
        for (EventEdge e : edges) {
            if (res == null || res.compareTo(e.getStartTime()) > 0) {
                res = e.getStartTime();
            }
        }
        return res == null ? BigDecimal.ZERO : res;
    }

    private Set<EventEdge> bfs(Collection<EntityNode> start, BackTrackConstraints constraints, boolean isBackward) {
        if (constraints.getStepConstraint() <= 0) return originalGraph.edgeSet();
        Map<EntityNode, Integer> map = new HashMap<>();
        Set<EventEdge> result = new HashSet<>();
        for (EntityNode n : start) {
            map.put(n, 0);
        }
        LinkedList<EntityNode> queue = new LinkedList<>(start);
        ConstraintExpression nodeConstraints = constraints.getNodeConstraints();
        while (!queue.isEmpty()) {
            EntityNode node = queue.pollFirst();
            int step = map.get(node);
            if (step < constraints.getStepConstraint()) {
                Set<EventEdge> edges = isBackward
                        ? originalGraph.incomingEdgesOf(node) : originalGraph.outgoingEdgesOf(node);
                result.addAll(edges);
                for (EventEdge edge : edges) {
                    EntityNode next = isBackward ? edge.getSource() : edge.getSink();
                    if (!map.containsKey(next) && (nodeConstraints == null || nodeConstraints.test(next))) {
                        map.put(next, step + 1);
                        queue.add(next);
                    }
                }
            }
        }
        return result;
    }
}
