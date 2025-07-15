package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;

import java.util.LinkedList;
import java.util.Map;

public class AndExpression implements ConstraintExpression {
    private final ConstraintExpression left;
    private final ConstraintExpression right;

    public AndExpression(ConstraintExpression l, ConstraintExpression r) {
        left = l;
        right = r;
    }

    @Override
    public boolean test(EntityNode node) {
        return left.test(node) && right.test(node);
    }

    @Override
    public boolean test(EventEdge edge) {
        return left.test(edge) && right.test(edge);
    }

    @Override
    public String toSQL(LinkedList<String> params) {
        return String.format("(%s) AND (%s)", left.toSQL(params), right.toSQL(params));
    }

    @Override
    public void setSqlTable(String tableName) {
        left.setSqlTable(tableName);
        right.setSqlTable(tableName);
    }

    @Override
    public String toCypher(String prefix, Map<String, Object> params) {
        return String.format("(%s) AND (%s)", left.toCypher(prefix, params), right.toCypher(prefix, params));
    };
}
