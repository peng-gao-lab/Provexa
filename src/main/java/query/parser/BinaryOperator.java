package query.parser;

import java.math.BigDecimal;

public enum BinaryOperator {
    Equal,
    NotEqual,
    LessThan,
    LessEqual,
    MoreThan,
    MoreEqual,
    Like;

    public boolean test(String v1, String v2) {
        if (v1 == null) return false;
        switch (this) {
            case Equal:
                return v1.equals(v2);
            case NotEqual:
                return !v1.equals(v2);
            case Like:
                // Remove the two % inserted by parser
                return v1.contains(v2.substring(1, v2.length() - 1));
        }
        return false;
    }

    public boolean test(long v1, long v2) {
        switch (this) {
            case Equal:
                return v1 == v2;
            case NotEqual:
                return v1 != v2;
            case LessThan:
                return v1 < v2;
            case LessEqual:
                return v1 <= v2;
            case MoreThan:
                return v1 > v2;
            case MoreEqual:
                return v1 >= v2;
        }
        return false;
    }

    public boolean test(BigDecimal v1, BigDecimal v2) {
        switch (this) {
            case Equal:
                return v1.equals(v2);
            case NotEqual:
                return !v1.equals(v2);
            case LessThan:
                return v1.compareTo(v2) < 0;
            case LessEqual:
                return v1.compareTo(v2) <= 0;
            case MoreThan:
                return v1.compareTo(v2) > 0;
            case MoreEqual:
                return v1.compareTo(v2) >= 0;
        }
        return false;
    }

    public String toSQL() {
        switch (this) {
            case NotEqual:
                return "<>";
            case LessThan:
                return "<";
            case LessEqual:
                return "<=";
            case MoreThan:
                return ">";
            case MoreEqual:
                return ">=";
            case Like:
                return " like ";
            case Equal:
            default:
                return "=";
        }
    }

    public String toCypher() {
        switch (this) {
            case NotEqual:
                return "<>";
            case LessThan:
                return "<";
            case LessEqual:
                return "<=";
            case MoreThan:
                return ">";
            case MoreEqual:
                return ">=";
            case Like:
                return " =~ ";
            case Equal:
            default:
                return "=";
        }
    }
}
