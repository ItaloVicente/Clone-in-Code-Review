
package com.couchbase.client.java.query.dsl.clause;

import static com.couchbase.client.java.query.dsl.Expression.x;

import java.util.ArrayList;

import com.couchbase.client.java.query.dsl.Expression;

public class UpdateForClause {

    private ArrayList<Expression> vars = new ArrayList<Expression>();

    private UpdateForClause() { }

    public static UpdateForClause forIn(String variable, String path) {
        UpdateForClause clause = new UpdateForClause();
        return clause.in(variable, path);
    }

    public static UpdateForClause forWithin(String variable, String path) {
        UpdateForClause clause = new UpdateForClause();
        return clause.within(variable, path);
    }

    public UpdateForClause in(String variable, String path) {
        Expression in = x(variable + " IN " + path);
        vars.add(in);
        return this;
    }

    public UpdateForClause within(String variable, String path) {
        vars.add(x(variable + " WITHIN " + path));
        return this;
    }

    public Expression when(Expression condition) {
        StringBuilder updateFor = new StringBuilder("FOR ");
        for (Expression var : vars) {
            updateFor.append(var.toString()).append(", ");
        }
        updateFor.delete(updateFor.length() - 2, updateFor.length());
        if (condition != null) {
            updateFor.append(" WHEN ").append(condition.toString());
        }
        updateFor.append(" END");
        return x(updateFor.toString());
    }

    public Expression end() {
        return when(null);
    }
}
