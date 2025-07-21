package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;

import java.util.LinkedList;
import java.util.Map;

public class BooleanExpression implements ConstraintExpression {
    private final boolean b;

    public BooleanExpression(boolean bool) {
        b = bool;
    }

    @Override
    public boolean test(EntityNode node) {
        return b;
    }

    @Override
    public boolean test(EventEdge edge) {
        return b;
    }

    @Override
    public String toSQL(LinkedList<String> params) {
        return b ? "1" : "0";
    }

    @Override
    public String toCypher(String prefix, Map<String, Object> params) {
        return b ? "1" : "0";
    }

    @Override
    public void setSqlTable(String value) {

    }
}
