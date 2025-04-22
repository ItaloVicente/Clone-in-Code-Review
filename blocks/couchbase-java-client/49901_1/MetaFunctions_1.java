package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.java.query.dsl.Expression;

public class ArrayFunctions {

    public static Expression arrayLength(Expression array) {
        return x("ARRAY_LENGTH(" + array.toString() + ")");
    }

    public static Expression arrayLength(String array) {
        return arrayLength(x(array));
    }
}
