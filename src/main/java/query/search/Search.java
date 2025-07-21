package query.search;

import org.jgrapht.graph.DirectedPseudograph;
import datamodel.EntityNode;
import datamodel.EventEdge;

import java.io.IOException;

public interface Search {
    void setup() throws Exception;
    DirectedPseudograph<EntityNode, EventEdge> search(SearchConstraints constraints) throws IOException;
}
