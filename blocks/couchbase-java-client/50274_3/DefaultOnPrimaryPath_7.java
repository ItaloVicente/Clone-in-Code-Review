package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.element.OnElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultOnPath extends AbstractPath implements OnPath {

    public DefaultOnPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public UsingWherePath on(String namespace, String keyspace, Expression expression, Expression... additionalExpressions) {
        element(new OnElement(namespace, keyspace, expression, additionalExpressions));
        return new DefaultUsingWherePath(this);
    }

    @Override
    public UsingWherePath on(String keyspace, Expression expression, Expression... additionalExpressions) {
        return on(null, keyspace, expression, additionalExpressions);
    }
}
