package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;

import java.util.LinkedList;
import java.util.Map;

public class OrExpression implements ConstraintExpression {
    private final ConstraintExpression left;
    private final ConstraintExpression right;

    public OrExpression(ConstraintExpression l, ConstraintExpression r) {
        left = l;
        right = r;
    }

    @Override
    public boolean test(EntityNode node) {
        return left.test(node) || right.test(node);
    }

    @Override
    public boolean test(EventEdge edge) {
        return left.test(edge) || right.test(edge);
    }

    @Override
    public String toSQL(LinkedList<String> params) {
        return String.format("(%s) OR (%s)", left.toSQL(params), right.toSQL(params));
    }

    @Override
    public void setSqlTable(String value) {
        left.setSqlTable(value);
        right.setSqlTable(value);
    }
    @Override
    public String toCypher(String prefix, Map<String, Object> params) {
        return String.format("(%s) OR (%s)", left.toCypher(prefix, params), right.toCypher(prefix, params));
    };
}
