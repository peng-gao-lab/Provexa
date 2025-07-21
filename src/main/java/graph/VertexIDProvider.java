package graph;

import org.jgrapht.io.ComponentNameProvider;
import datamodel.EntityNode;

public class VertexIDProvider implements ComponentNameProvider<EntityNode> {
    @Override
    public String getName(EntityNode n) {
        return Long.toString(n.getID());
    }
}
