package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.java.query.dsl.Expression;

public class AggregateFunctions {

    public static Expression count(Expression expression) {
        return x("COUNT(" + expression.toString() + ")");
    }

    public static Expression count(String expression) {
        return count(x(expression));
    }

    public static Expression avg(Expression expression) {
        return x("AVG(" + expression.toString() + ")");
    }

    public static Expression avg(String expression) {
        return avg(x(expression));
    }
}
