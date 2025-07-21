package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;
import org.jgrapht.graph.DirectedPseudograph;

public interface GraphQuery {
    DirectedPseudograph<EntityNode, EventEdge> execute() throws Exception;
}
