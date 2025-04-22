package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class ConditionalFunctions {

    protected static Expression build(String operator, Expression expression1, Expression expression2,
            Expression... others) {
        StringBuilder result = new StringBuilder(operator);
        result.append('(').append(expression1.toString()).append(", ").append(expression2.toString());
        if (others != null) {
            for (Expression other : others) {
                if (other == null) {
                    other = Expression.NULL();
                }
                result.append(", ").append(other.toString());
            }
        }
        result.append(')');
        return x(result.toString());
    }


    public static Expression ifMissing(Expression expression1, Expression expression2, Expression... others) {
        return build("IFMISSING", expression1, expression2, others);
    }

    public static Expression ifMissingOrNull(Expression expression1, Expression expression2, Expression... others) {
        return build("IFMISSINGORNULL", expression1, expression2, others);
    }

    public static Expression ifNull(Expression expression1, Expression expression2, Expression... others) {
        return build("IFNULL", expression1, expression2, others);
    }

    public static Expression missingIf(Expression expression1, Expression expression2) {
        return x("MISSINGIF(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression nullIf(Expression expression1, Expression expression2) {
        return x("NULLIF(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression ifInf(Expression expression1, Expression expression2, Expression... others) {
        return build("IFINF", expression1, expression2, others);
    }

    public static Expression ifNaN(Expression expression1, Expression expression2, Expression... others) {
        return build("IFNAN", expression1, expression2, others);
    }

    public static Expression ifNaNOrInf(Expression expression1, Expression expression2, Expression... others) {
        return build("IFNANORINF", expression1, expression2, others);
    }

    public static Expression nanIf(Expression expression1, Expression expression2) {
        return x("NANIF(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression negInfIf(Expression expression1, Expression expression2) {
        return x("NEGINFIF(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression posInfIf(Expression expression1, Expression expression2) {
        return x("POSINFIF(" + expression1.toString() + ", " + expression2.toString() + ")");
    }
}
