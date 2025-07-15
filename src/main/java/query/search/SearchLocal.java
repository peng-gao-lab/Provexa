package query.search;

import datamodel.EntityNode;
import datamodel.EventEdge;
import datamodel.FileEntity;
import org.jgrapht.graph.DirectedPseudograph;
import query.backtracking.BackTrackConstraints;
import query.parser.ConstraintExpression;
import scala.collection.immutable.Stream;

import java.io.IOException;
import java.util.*;

import static query.Utils.getInterval;

public class SearchLocal implements Search{
    DirectedPseudograph<EntityNode, EventEdge> source = null;
    @Override
    public void setup() throws Exception {}
    public void setup(DirectedPseudograph<EntityNode, EventEdge> source) throws Exception {
        this.source = (DirectedPseudograph<EntityNode, EventEdge>) source.clone();
    }
    public SearchLocal() {}
    public SearchLocal(DirectedPseudograph<EntityNode, EventEdge> source) {
        this.source = (DirectedPseudograph<EntityNode, EventEdge>) source.clone();
    }

    @Override
    public DirectedPseudograph<EntityNode, EventEdge> search(SearchConstraints constraints) throws IOException {
        long sTime = System.currentTimeMillis();
        DirectedPseudograph<EntityNode, EventEdge> graph = new DirectedPseudograph<>(EventEdge.class);
        // Match edges
        LinkedList<LinkedList<LinkedList<EventEdge>>> matchedEdges = matchEdges(constraints);
        // Deal with the matched Edge
        LinkedList<LinkedList<EventEdge>> allPaths = processMatchedEdge(constraints, matchedEdges);
        // Add all edges to graph
        LinkedList<EventEdge> path = allPaths.pollLast();
        int srcId, dstId;
        while (path != null) {
            EventEdge edge = path.pollLast();
            while (edge != null) {
                EntityNode sourceNode = edge.getSource();
                EntityNode sinkNode = edge.getSink();
                graph.addVertex(sourceNode);
                graph.addVertex(sinkNode);
                graph.addEdge(sourceNode, sinkNode, edge);
                edge = path.pollLast();
            }
            path = allPaths.pollLast();
        }
        long eTime = System.currentTimeMillis();
        System.out.println("Finished search" + " in " + (eTime - sTime) + "ms");
        return graph;
    }

    public LinkedList<LinkedList<LinkedList<EventEdge>>> matchEdges(SearchConstraints constraints) {
        ArrayList<ArrayList<String>> relations = constraints.getEdges();
        HashMap<String, ConstraintExpression> nodeConst = constraints.getNodeConstraints();
        LinkedList<LinkedList<LinkedList<EventEdge>>> allEdges = new LinkedList<>();
        String preSinkID = "null";
        LinkedList<EntityNode> preSinkNodes = new LinkedList<>();
        for (ArrayList<String> relation : relations) {
            LinkedList<LinkedList<EventEdge>> edges = new LinkedList<>();
            String sourceID = relation.get(0);
            String opt = relation.get(1);
            String sinkID = relation.get(2);
            ConstraintExpression sourceConst = nodeConst.get(sourceID);
            ConstraintExpression sinkConst = nodeConst.get(sinkID);
            List<EntityNode> sourceNodes;
            if (!preSinkID.equals(sourceID)) {
                sourceNodes = getVertices(sourceConst);
            } else {
                sourceNodes = preSinkNodes;
                preSinkNodes = new LinkedList<>();
            }
//            System.out.println("Source Nodes: " + sourceNodes);
            for (EntityNode sourceNode : sourceNodes) {
                for (EventEdge edge : this.source.outgoingEdgesOf(sourceNode)) {
//                    System.out.println("Edges: " + edge);
                    EntityNode sinkNode = edge.getSink();
                    if (sinkConst.test(sinkNode)) {
                        preSinkNodes.add(sinkNode);
                        LinkedList<EventEdge> tmp = new LinkedList<>();
                        tmp.add(edge);
                        edges.add(tmp);
                    }
                }
            }
//            System.out.println("Sink Nodes: " + preSinkNodes);
            preSinkID = sinkID;
            allEdges.add(edges);
        }
//        System.out.println(allEdges);
        return allEdges;
    }

    private List<EntityNode> getVertices(ConstraintExpression constExp) {
        ArrayList<EntityNode> result = new ArrayList<>();
        for (EntityNode n : this.source.vertexSet()) {
            if (constExp.test(n)) {
                result.add(n);
            }
        }
        return result;
    }

    public LinkedList<LinkedList<EventEdge>> processMatchedEdge(SearchConstraints constraints, LinkedList<LinkedList<LinkedList<EventEdge>>> matchedEdges) {
        ArrayList<List<String>> optsBak = constraints.getEdgeConstraints();
        ArrayList<List<String>> opts = (ArrayList<List<String>>) optsBak.clone();
        LinkedList<LinkedList<EventEdge>> allPaths = new LinkedList<>();
        while (matchedEdges.size() > 1) {
            // Find the smallest join
            int idx = 0;
            int maxValue = Integer.MAX_VALUE;
            for (int i=0; i<matchedEdges.size()-1; i++) {
                LinkedList<LinkedList<EventEdge>> prePath = matchedEdges.get(i);
                LinkedList<LinkedList<EventEdge>> postPath = matchedEdges.get(i+1);
                if (prePath.size() * postPath.size() < maxValue) {
                    idx = i;
                }
            }
            List<String> opt = opts.get(idx);
            LinkedList<LinkedList<EventEdge>> newPath = combineTwoPaths(matchedEdges.get(idx), matchedEdges.get(idx+1), opt);
            // Replace original two paths with one combined path
            // Remove original paths
            matchedEdges.remove(idx);
            matchedEdges.remove(idx);
            opts.remove(idx);
            // Add new path
            matchedEdges.add(idx, newPath);
        }
        for (LinkedList<LinkedList<EventEdge>> edges : matchedEdges) {
            allPaths.addAll(edges);
        }
        return allPaths;
    }

    public LinkedList<LinkedList<EventEdge>> combineTwoPaths(LinkedList<LinkedList<EventEdge>> path1,
                                                             LinkedList<LinkedList<EventEdge>> path2,
                                                             List<String> opt) {
        LinkedList<LinkedList<EventEdge>> combinedEdge = new LinkedList<>();
        double time = getInterval(opt);
        try {
            LinkedList<EventEdge> prePath = path1.removeFirst();
            while (prePath!=null) {
                EventEdge lastEdge = prePath.getLast();
                for (LinkedList<EventEdge> postPath : path2) {
                    EventEdge edge = postPath.getFirst();
                    if (edge.getStartTime().subtract(lastEdge.getEndTime()).doubleValue() < time) {
                        LinkedList<EventEdge> newPath = new LinkedList<>();
                        newPath.addAll(prePath);
                        newPath.addAll(postPath);
                        combinedEdge.addLast(newPath);
                    }
                }
                prePath = path1.removeFirst();
            }
        } catch (NoSuchElementException exp){
//            System.out.println("Path finished");
        }
        return combinedEdge;
    }

}
