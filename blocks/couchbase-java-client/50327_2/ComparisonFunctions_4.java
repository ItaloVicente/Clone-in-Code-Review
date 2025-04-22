package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class ComparisonFunctions {
    public static Expression greatest(Expression e1, Expression e2, Expression... otherExpressions) {
        StringBuilder greatest = new StringBuilder("GREATEST(");
        greatest.append(e1.toString()).append(", ").append(e2.toString());
        if (otherExpressions == null) {
            return x(greatest.append(')').toString());
        }

        for (Expression otherExpression : otherExpressions) {
            if (otherExpression == null) {
                otherExpression = Expression.NULL();
            }
            greatest.append(", ").append(otherExpression.toString());
        }
        greatest.append(')');
        return x(greatest.toString());
    }

    public static Expression least(Expression e1, Expression e2, Expression... otherExpressions) {
        StringBuilder least = new StringBuilder("LEAST(");
        least.append(e1.toString()).append(", ").append(e2.toString());
        if (otherExpressions == null) {
            return x(least.append(')').toString());
        }

        for (Expression otherExpression : otherExpressions) {
            if (otherExpression == null) {
                otherExpression = Expression.NULL();
            }
            least.append(", ").append(otherExpression.toString());
        }
        least.append(')');
        return x(least.toString());
    }
}
