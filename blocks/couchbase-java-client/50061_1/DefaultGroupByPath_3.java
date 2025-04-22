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

}
