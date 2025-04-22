package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class TypeFunctions {


    public static Expression isArray(Expression expression) {
        return x("ISARRAY(" + expression.toString() + ")");
    }

    public static Expression isArray(String expression) {
        return isArray(x(expression));
    }

    public static Expression isAtom(Expression expression) {
        return x("ISATOM(" + expression.toString() + ")");
    }

    public static Expression isAtom(String expression) {
        return isAtom(x(expression));
    }

    public static Expression isBoolean(Expression expression) {
        return x("ISBOOLEAN(" + expression.toString() + ")");
    }

    public static Expression isBoolean(String expression) {
        return isBoolean(x(expression));
    }

    public static Expression isNumber(Expression expression) {
        return x("ISNUMBER(" + expression.toString() + ")");
    }

    public static Expression isNumber(String expression) {
        return isNumber(x(expression));
    }

    public static Expression isObject(Expression expression) {
        return x("ISOBJECT(" + expression.toString() + ")");
    }

    public static Expression isObject(String expression) {
        return isObject(x(expression));
    }

    public static Expression isString(Expression expression) {
        return x("ISSTRING(" + expression.toString() + ")");
    }

    public static Expression isString(String expression) {
        return isString(x(expression));
    }

    public static Expression type(Expression expression) {
        return x("TYPE(" + expression.toString() + ")");
    }

    public static Expression type(String expression) {
        return type(x(expression));
    }



    public static Expression toArray(Expression expression) {
        return x("TOARRAY(" + expression.toString() + ")");
    }

    public static Expression toArray(String expression) {
        return toArray(x(expression));
    }

    public static Expression toAtom(Expression expression) {
        return x("TOATOM(" + expression.toString() + ")");
    }

    public static Expression toAtom(String expression) {
        return toAtom(x(expression));
    }

    public static Expression toBoolean(Expression expression) {
        return x("TOBOOLEAN(" + expression.toString() + ")");
    }

    public static Expression toBoolean(String expression) {
        return toBoolean(x(expression));
    }


    public static Expression toNumber(Expression expression) {
        return x("TONUMBER(" + expression.toString() + ")");
    }

    public static Expression toNumber(String expression) {
        return toNumber(x(expression));
    }

    public static Expression toObject(Expression expression) {
        return x("TOOBJECT(" + expression.toString() + ")");
    }

    public static Expression toObject(String expression) {
        return toObject(x(expression));
    }

    public static Expression toString(Expression expression) {
        return x("TOSTRING(" + expression.toString() + ")");
    }

    public static Expression toString(String expression) {
        return toString(x(expression));
    }
}
