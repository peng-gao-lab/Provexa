package query.parser;

import datamodel.EntityNode;
import datamodel.EventEdge;

import java.util.*;

public class InExpression implements ConstraintExpression {
    private final HashSet<Long> ids;
    private String sqlTable = "";

    public InExpression(Collection<Long> ids) {
        this.ids = new HashSet<>(ids);
    }

    @Override
    public boolean test(EntityNode node) {
        return ids.contains(node.getID());
    }

    @Override
    public boolean test(EventEdge edge) {
        return ids.contains(edge.getID());
    }

    @Override
    public String toSQL(LinkedList<String> params) {
        StringJoiner sj = new StringJoiner(",");
        for (long id : ids) {
            sj.add(Long.toString(id));
        }
        return String.format("%sid IN (%s)", sqlTable, sj.toString());
    }

    @Override
    public void setSqlTable(String value) {
        sqlTable = value + ".";
    }

    @Override
    public String toCypher(String prefix, Map<String, Object> params) {
        StringJoiner sj = new StringJoiner(",");
        for (long id : ids) {
            sj.add(Long.toString(id));
        }
        return String.format("%s.id IN [%s]", prefix, sj.toString());
    };
}
