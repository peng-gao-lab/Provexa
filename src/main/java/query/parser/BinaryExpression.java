package query.parser;

import datamodel.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class BinaryExpression implements ConstraintExpression {
    private final String key;
    private final BinaryOperator operator;
    private final String value;
    private Long numerical;
    private BigDecimal time;
    private String sqlTable = "";

    public BinaryExpression(String k, BinaryOperator op, String v) {
        key = k;
        operator = op;
        value = v;
    }

    public BinaryExpression(String k, BinaryOperator op, long v) {
        key = k;
        operator = op;
        value = Long.toString(v);
        numerical = v;
        time = BigDecimal.valueOf(v);
    }

    public BinaryExpression(String k, BinaryOperator op, BigDecimal t) {
        key = k;
        operator = op;
        value = t.toString();
        numerical = t.longValue();
        time = t;
    }

    @Override
    public void setSqlTable(String tableName) {
        sqlTable = tableName + ".";
    }

    @Override
    public boolean test(EntityNode node) {
        if (key.equals("id")) {
            return operator.test(node.getID(), numerical);
        }
        if (node.getF() != null) {
            FileEntity f = node.getF();
            switch (key) {
                case "type":
                    return operator.test("file", value);
                case "name":
                    return operator.test(f.getName(), value);
                case "path":
                    return operator.test(f.getPath(), value);
            }
        } else if (node.getN() != null) {
            NetworkEntity n = node.getN();
            switch (key) {
                case "type":
                    return operator.test("network", value);
                case "dstip":
                    return operator.test(n.getDstAddress(), value);
                case "dstport":
                    return operator.test(n.getDstPort(), value);
                case "srcip":
                    return operator.test(n.getSrcAddress(), value);
                case "srcport":
                    return operator.test(n.getSrcPort(), value);
            }
        } else if (node.getP() != null) {
            ProcessEntity p = node.getP();
            switch (key) {
                case "type":
                    return operator.test("process", value);
                case "pid":
                    return operator.test(p.getPid(), value);
                case "exename":
                    return operator.test(p.getName(), value);
                case "exepath":
                    return operator.test(p.getExePath(), value);
                case "cmdline":
                    return operator.test(p.getCmdLine(), value);
            }
        }
        return false;
    }

    @Override
    public boolean test(EventEdge edge) {
        switch (key) {
            case "id":
                return operator.test(edge.getID(), numerical);
            case "srcid":
                return operator.test(edge.getSource().getID(), numerical);
            case "dstid":
                return operator.test(edge.getSink().getID(), numerical);
            case "starttime":
                return operator.test(edge.getStartTime(), time);
            case "endtime":
                return operator.test(edge.getEndTime(), time);
            case "optype":
                return operator.test(edge.getEvent(), value);
            case "amount":
                return operator.test(edge.getSize(), numerical);
        }
        return false;
    }

    @Override
    public String toSQL(LinkedList<String> params) {
        if (numerical == null && time == null) {
            params.add(value);
            return String.format("%s%s %s ?", sqlTable, key, operator.toSQL());
        } else {
            return String.format("%s%s %s %s", sqlTable, key, operator.toSQL(), value);
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public String toCypher(String prefix, Map<String, Object> params) {
        String varName = getSaltString();
        if (numerical == null) {
            if (operator == BinaryOperator.Like) {
                String nvalue = ".*" + value.substring(1, value.length() - 1) + ".*";
                params.put(varName, nvalue);
            } else {
                params.put(varName, value);
            }
        } else {
            params.put(varName, numerical);
        }
        return String.format("%s.%s %s %s", prefix, key, operator.toCypher(), "$" + varName);
    };
}
