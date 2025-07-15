package graph;

import org.jgrapht.io.ComponentNameProvider;
import datamodel.EntityNode;

public class VertexLabelProvider implements ComponentNameProvider<EntityNode> {
    @Override
    public String getName(EntityNode e) {
        String sig = e.getSignature();
        if(sig.startsWith("=")){
            sig = e.getSignature().substring(1);
        }
        sig = sig.replaceAll("\\\\", "\\\\\\\\");
        return sig;
    }
}
