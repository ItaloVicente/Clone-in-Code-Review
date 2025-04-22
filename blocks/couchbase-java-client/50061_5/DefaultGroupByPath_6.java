package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class StringFunctions {

    public static Expression lower(Expression expression) {
        return x("LOWER(" + expression.toString() + ")");
    }

    public static Expression lower(String identifier) {
        return lower(x(identifier));
    }

    public static Expression substr(Expression expression, int position, int length) {
        return x("SUBSTR(" + expression.toString() + ", " + position + ", " + length + ")");
    }

    public static Expression substr(String expression, int position, int length) {
        return x("SUBSTR(" + expression.toString() + ", " + position + ", " + length + ")");
    }

    public static Expression substr(Expression expression, int position) {
        return x("SUBSTR(" + expression.toString() + ", " + position + ")");
    }

    public static Expression substr(String expression, int position) {
        return x("SUBSTR(" + expression.toString() + ", " + position + ")");
    }



}
