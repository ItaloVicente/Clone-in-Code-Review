package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.java.query.dsl.Expression;

public class MetaFunctions {

    public static Expression meta(Expression expression) {
        return x("META(" + expression.toString() + ")");
    }

    public static Expression meta(String expression) {
        return meta(x(expression));
    }

    public static Expression base64(Expression expression) {
        return x("BASE64(" + expression + ")");
    }

    public static Expression base64(String expression) {
        return base64(x(expression));
    }

    public static Expression uuid() {
        return x("UUID()");
    }
}
