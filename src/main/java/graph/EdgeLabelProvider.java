package graph;

import org.jgrapht.io.ComponentNameProvider;
import datamodel.EventEdge;

public class EdgeLabelProvider implements ComponentNameProvider<EventEdge> {
    @Override
    public String getName(EventEdge eventEdge) {
        String sig = eventEdge.getID()+ " " + eventEdge.getEvent();
        sig = sig.replaceAll("\\\\", "\\\\\\\\");
        return sig;
    }
}
