package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class OnElement implements Element {

    private final String fullKeyspace;
    private final Expression expression;
    private final Expression[] additionalExpressions;

    public OnElement(String namespace, String keyspace, Expression expression,
            Expression[] additionalExpressions) {
        this.fullKeyspace = (namespace == null) ?
                "`" + keyspace + "`" :
                "`" + namespace + "`:`" + keyspace + "`";
        this.expression = expression;
        this.additionalExpressions = additionalExpressions;
    }

    @Override
    public String export() {
        if (expression == null) {
            return "ON " + fullKeyspace;
        } else {
            if (additionalExpressions == null || additionalExpressions.length == 0) {
                return "ON " + fullKeyspace + "(" + expression.toString() + ")";
            } else {
                StringBuilder on = new StringBuilder("ON ");
                on.append(fullKeyspace).append('(').append(expression);
                for (Expression additionalExpression : additionalExpressions) {
                    on.append(", ").append(additionalExpression.toString());
                }
                on.append(')');
                return on.toString();
            }
        }
    }
}
