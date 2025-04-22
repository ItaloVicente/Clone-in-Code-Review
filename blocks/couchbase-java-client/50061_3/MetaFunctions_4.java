package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DateFunctions {

    public static Expression strToMillis(Expression expression) {
        return strToMillis(expression.toString());
    }

    public static Expression strToMillis(String expression) {
        return x("STR_TO_MILLIS(" + expression + ")");
    }

    public static Expression datePartStr(Expression expression, String part) {
        return datePartStr(expression.toString(), part);
    }

    public static Expression datePartStr(String expression, String part) {
        return x("DATE_PART_STR(" + expression + ",\"" + part + "\")");
    }
}
