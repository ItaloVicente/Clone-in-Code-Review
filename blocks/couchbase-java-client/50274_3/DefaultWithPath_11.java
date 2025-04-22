package com.couchbase.client.java.query.dsl.path.index;

import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.element.WhereElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultWherePath extends DefaultWithPath implements WherePath {

    protected DefaultWherePath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public WithPath where(Expression filterExpression) {
        element(new WhereElement(filterExpression));
        return new DefaultWithPath(this);
    }

    @Override
    public WithPath where(String filterExpression) {
        return where(x(filterExpression));
    }
}
