package query.backtracking;

import org.jgrapht.graph.DirectedPseudograph;
import datamodel.EntityNode;
import datamodel.EventEdge;

import java.io.IOException;

public interface BackTrack {
    void setup() throws Exception;
    DirectedPseudograph<EntityNode, EventEdge> backTrackPOIEvent(BackTrackConstraints constraints) throws IOException;
    DirectedPseudograph<EntityNode, EventEdge> forwardTrackPOIEvent(BackTrackConstraints constraints) throws IOException;
}
