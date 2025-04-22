package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class OnElement implements Element {

    private final String keyspace;
    private final Expression expression;
    private final Expression[] additionalExpressions;

    public OnElement(String keyspace, Expression expression,
            Expression[] additionalExpressions) {
        this.keyspace = keyspace;
        this.expression = expression;
        this.additionalExpressions = additionalExpressions;
    }

    @Override
    public String export() {
        if (expression == null) {
            return "ON " + keyspace + "";
        } else {
            if (additionalExpressions == null || additionalExpressions.length == 0) {
                return "ON " + keyspace + "(" + expression.toString() + ")";
            } else {
                StringBuilder on = new StringBuilder("ON ");
                on.append(keyspace).append('(').append(expression);
                for (Expression additionalExpression : additionalExpressions) {
                    on.append(", ").append(additionalExpression.toString());
                }
                on.append(')');
                return on.toString();
            }
        }
    }
}
