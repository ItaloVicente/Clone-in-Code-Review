package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PatternMatchingFunctions {

    public static Expression regexpContains(Expression expression, String pattern) {
        return x("REGEXP_CONTAINS(" + expression.toString() + ", \"" + pattern + "\")");
    }

    public static Expression regexpContains(String expression, String pattern) {
        return regexpContains(x(expression), pattern);
    }

    public static Expression regexpLike(Expression expression, String pattern) {
        return x("REGEXP_LIKE(" + expression.toString() + ", \"" + pattern + "\")");
    }

    public static Expression regexpLike(String expression, String pattern) {
        return regexpLike(x(expression), pattern);
    }

    public static Expression regexpPosition(Expression expression, String pattern) {
        return x("REGEXP_POSITION(" + expression.toString() + ", \"" + pattern + "\")");
    }

    public static Expression regexpPosition(String expression, String pattern) {
        return regexpPosition(x(expression), pattern);
    }

    public static Expression regexpReplace(Expression expression, String pattern, String repl, int n) {
        return x("REGEXP_REPLACE(" + expression.toString() + ", \"" + pattern + "\", \"" + repl + "\", " + n + ")");
    }

    public static Expression regexpReplace(String expression, String pattern, String repl, int n) {
        return regexpReplace(x(expression), pattern, repl, n);
    }

    public static Expression regexpReplace(Expression expression, String pattern, String repl) {
        return x("REGEXP_REPLACE(" + expression.toString() + ", \"" + pattern + "\", \"" + repl + "\")");
    }

    public static Expression regexpReplace(String expression, String pattern, String repl) {
        return regexpReplace(x(expression), pattern, repl);
    }
}
