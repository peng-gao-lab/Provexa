package graph;

import datamodel.*;
import parser.sysdigparser.ProcessOriginalParserOutput;
import org.jgrapht.graph.DirectedPseudograph;
import java.util.*;

public class GlobalDependencyGraph {
    public DirectedPseudograph<EntityNode, EventEdge> jg;
    private HashMap<Long,EntityNode> entityNodeMap;
    private ProcessOriginalParserOutput sysdigParserOutput;
    public EntityNode POIEvent;

    public GlobalDependencyGraph(ProcessOriginalParserOutput parserOutput){
        jg = new DirectedPseudograph<>(EventEdge.class);
        POIEvent = null;
        entityNodeMap = new HashMap<>();
        sysdigParserOutput = parserOutput;
        //sysdigProcess.reverseSourceAndSink();
    }

    public DirectedPseudograph<EntityNode, EventEdge> getJg() {
        return jg;
    }

    public void GenerateGraph(){
        addFileToProcessEvent(sysdigParserOutput.getFileProcessMap());
        addNetworkToProcessEvent(sysdigParserOutput.getNetworkProcessMap());
        addProcessToFileEvent(sysdigParserOutput.getProcessFileMap());
        addProcessToProcessEvent(sysdigParserOutput.getProcessProcessMap());
        addProcessToNetworkEvent(sysdigParserOutput.getProcessNetworkMap());
        assignEdgeId();
    }

    private void assignEdgeId() {
        long edgeID = 1;
        for (EventEdge e : jg.edgeSet()){
            e.id = edgeID;
            edgeID++;
        }
    }

    private EntityNode createNodeFromEntity(Entity e) {
        if (e instanceof FileEntity || e instanceof ProcessEntity || e instanceof NetworkEntity) {
            return new EntityNode(e);
        }
        return null;
    }

    private void addEventToGraph(EntityEvent event, EventEdge edge) {
        EntityNode source, sink;
        if (entityNodeMap.containsKey(event.getSource().getUniqID())) {
            source = entityNodeMap.get(event.getSource().getUniqID());
        } else {
            source = createNodeFromEntity(event.getSource());
            entityNodeMap.put(source.getID(), source);
        }

        if (entityNodeMap.containsKey(event.getSink().getUniqID())) {
            sink = entityNodeMap.get(event.getSink().getUniqID());
        } else {
            sink = createNodeFromEntity(event.getSink());
            entityNodeMap.put(sink.getID(), sink);
        }

        jg.addVertex(source);
        jg.addVertex(sink);
        jg.addEdge(source, sink, edge);
    }

    private void addProcessToFileEvent(Map<String, PtoFEvent> pfmap) {
        for(PtoFEvent event: pfmap.values()){
            EventEdge edge = new EventEdge(event);
            addEventToGraph(event, edge);
        }
    }

    private void addFileToProcessEvent(Map<String, FtoPEvent> fpmap) {
        for(FtoPEvent event: fpmap.values()){
            EventEdge edge = new EventEdge(event);
            addEventToGraph(event, edge);
        }
    }

    private void addProcessToProcessEvent(Map<String, PtoPEvent> ppmap) {
        for(PtoPEvent event: ppmap.values()){
            EventEdge edge = new EventEdge(event);
            addEventToGraph(event, edge);
        }
    }

    private void addNetworkToProcessEvent(Map<String, NtoPEvent> npmap) {
        for(NtoPEvent event: npmap.values()){
            EventEdge edge = new EventEdge(event);
            addEventToGraph(event, edge);
        }
    }

    private void addProcessToNetworkEvent(Map<String, PtoNEvent> pnmap) {
        for(PtoNEvent event: pnmap.values()){
            EventEdge edge = new EventEdge(event);
            addEventToGraph(event, edge);
        }
    }
}
