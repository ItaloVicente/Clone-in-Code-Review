package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.UsingElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultUsingWherePath extends DefaultWherePath implements UsingWherePath {
    protected DefaultUsingWherePath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public WherePath using(IndexType indexType) {
        element(new UsingElement(indexType));
        return new DefaultWherePath(this);
    }
}
