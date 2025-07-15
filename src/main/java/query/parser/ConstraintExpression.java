package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;

import java.util.LinkedList;
import java.util.Map;

public interface ConstraintExpression {
    boolean test(EntityNode node);
    boolean test(EventEdge edge);
    String toSQL(LinkedList<String> params);
    String toCypher(String prefix, Map<String, Object> params);
    void setSqlTable(String value);
}
