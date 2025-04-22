package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.UsingElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultUsingPath extends DefaultWithPath implements UsingPath {

    protected DefaultUsingPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public WithPath using(IndexType type) {
        element(new UsingElement(type));
        return new DefaultWithPath(this);
    }
}
