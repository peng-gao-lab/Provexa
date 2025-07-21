package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;

import java.util.LinkedList;
import java.util.Map;

public class NotExpression implements ConstraintExpression {
    private final ConstraintExpression child;

    public NotExpression(ConstraintExpression child) {
        this.child = child;
    }

    @Override
    public boolean test(EntityNode node) {
        return !child.test(node);
    }

    @Override
    public boolean test(EventEdge edge) {
        return !child.test(edge);
    }

    @Override
    public String toSQL(LinkedList<String> params) {
        return String.format("NOT (%s)", child.toSQL(params));
    }

    @Override
    public void setSqlTable(String value) {
        child.setSqlTable(value);
    }

    @Override
    public String toCypher(String prefix, Map<String, Object> params) {
        return String.format("NOT (%s)", child.toCypher(prefix, params));
    };
}
