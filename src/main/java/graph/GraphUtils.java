package graph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxCellRenderer;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.w3c.dom.Document;
import datamodel.*;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedPseudograph;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;
import java.util.*;

public class GraphUtils {
    DirectedPseudograph<EntityNode, EventEdge> graph;
    DOTExporter<EntityNode, EventEdge> exporter;
    Map<String, EntityNode> indexOfNode;

    public GraphUtils(DirectedPseudograph<EntityNode, EventEdge> graph){
        this.graph = graph;
        exporter = new DOTExporter<>(new VertexIDProvider(), new VertexLabelProvider(), new EdgeLabelProvider());
        indexOfNode = new HashMap<>();
        for(EntityNode n : graph.vertexSet()){
            indexOfNode.put(n.getSignature(), n);
        }
    }

    public void exportGraphDot(String fileName){
        try {
            String dotName = String.format("%s.dot", fileName);
            exporter.exportGraph(graph, new FileWriter(dotName));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String exportGraphDotString() {
        StringWriter writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        return writer.toString();
    }

    public void exportGraphSvg(String fileName) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            exporter.exportGraph(graph, stream);
            MutableGraph graphVizGraph = new Parser().read(stream.toString());
            String svgName = String.format("%s.svg", fileName);
            File file = new File(svgName);
            Graphviz.fromGraph(graphVizGraph).render(Format.SVG).toFile(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exportGraphSvgJGraphX(String fileName) {
        JGraphXAdapter<EntityNode, EventEdge> graphMx = new JGraphXAdapter<>(graph);
        HashMap<EntityNode, mxICell> nodeMap = graphMx.getVertexToCellMap();
        HashMap<EventEdge, mxICell> edgeMap = graphMx.getEdgeToCellMap();

        VertexLabelProvider vl = new VertexLabelProvider();
        for (EntityNode n : nodeMap.keySet()) {
            mxICell cell = nodeMap.get(n);
            cell.setValue(vl.getName(n));
        }

        EdgeLabelProvider el = new EdgeLabelProvider();
        for (EventEdge e : edgeMap.keySet()) {
            mxICell cell = edgeMap.get(e);
            cell.setValue(el.getName(e));
        }

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graphMx);
        layout.execute(graphMx.getDefaultParent());
        Document svg = mxCellRenderer.createSvgDocument(graphMx, null, 1, Color.WHITE, null);
        String svgName = String.format("%s.svg", fileName);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            FileWriter writer = new FileWriter(svgName);
            StreamResult result = new StreamResult(writer);
            DOMSource source = new DOMSource(svg);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DirectedPseudograph<EntityNode, EventEdge> bfs(String input){
        EntityNode start = getGraphVertex(input);
        if (start != null) {
            return bfs(start);
        } else {
            System.out.println("Input doesn't exist in the graph");
            return null;
        }
    }

    private DirectedPseudograph<EntityNode, EventEdge> bfs(EntityNode start){
        Queue<EntityNode> queue = new LinkedList<>();
        DirectedPseudograph<EntityNode, EventEdge> newGraph = new DirectedPseudograph<>(EventEdge.class);
        queue.offer(start);
        Set<EntityNode> nodeInTheQueue = new HashSet<>();
        nodeInTheQueue.add(start);

        while (!queue.isEmpty()) {
            EntityNode cur = queue.poll();
            newGraph.addVertex(cur);
            Set<EventEdge> inEdges = graph.incomingEdgesOf(cur);
            for (EventEdge edge: inEdges) {
                EntityNode source = edge.getSource();
                newGraph.addVertex(source);
                newGraph.addEdge(source,cur,edge);
                if (!nodeInTheQueue.contains(source)) {
                    nodeInTheQueue.add(source);
                    queue.offer(source);
                }
            }
            Set<EventEdge> outEdges = graph.outgoingEdgesOf(cur);
            for (EventEdge edge: outEdges) {
                EntityNode target = edge.getSink();
                newGraph.addVertex(target);
                newGraph.addEdge(cur, target, edge);
                if (!nodeInTheQueue.contains(target)) {
                    nodeInTheQueue.add(target);
                    queue.offer(target);
                }
            }
        }
        return newGraph;
    }

    public EntityNode getGraphVertex(String input){
        if (indexOfNode.containsKey(input)) {
            return indexOfNode.get(input);
        }
        return null;
    }
}
