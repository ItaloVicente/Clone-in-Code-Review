package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class NumberFunctions {

    public static Expression round(Expression expression) {
        return x("ROUND(" + expression.toString() + ")");
    }

    public static Expression round(Expression expression, int digits) {
        return x("ROUND(" + expression.toString() + ", " + digits + ")");
    }

    public static Expression round(Number expression) {
        return round(x(expression));
    }

    public static Expression round(Number expression, int digits) {
        return round(x(expression), digits);
    }
}
