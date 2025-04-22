package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.OnElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultOnPrimaryPath extends AbstractPath implements OnPrimaryPath {

    protected DefaultOnPrimaryPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public UsingPath on(String namespace) {
        element(new OnElement(namespace, null, null));
        return new DefaultUsingPath(this);
    }
}
