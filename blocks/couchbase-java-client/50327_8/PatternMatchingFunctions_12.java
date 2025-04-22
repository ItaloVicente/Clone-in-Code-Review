package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class ObjectFunctions {

    public static Expression objectLength(Expression expression) {
        return x("OBJECT_LENGTH(" + expression.toString() + ")");
    }

    public static Expression objectLength(String expression) {
        return objectLength(x(expression));
    }

    public static Expression objectLength(JsonObject value) {
        return objectLength(x(value));
    }

    public static Expression objectNames(Expression expression) {
        return x("OBJECT_NAMES(" + expression.toString() + ")");
    }

    public static Expression objectNames(String expression) {
        return objectNames(x(expression));
    }

    public static Expression objectNames(JsonObject value) {
        return objectNames(x(value));
    }

    public static Expression objectPairs(Expression expression) {
        return x("OBJECT_PAIRS(" + expression.toString() + ")");
    }

    public static Expression objectPairs(String expression) {
        return objectPairs(x(expression));
    }

    public static Expression objectPairs(JsonObject value) {
        return objectPairs(x(value));
    }

    public static Expression objectValues(Expression expression) {
        return x("OBJECT_VALUES(" + expression.toString() + ")");
    }

    public static Expression objectValues(String expression) {
        return objectValues(x(expression));
    }

    public static Expression objectValues(JsonObject value) {
        return objectValues(x(value));
    }
}
