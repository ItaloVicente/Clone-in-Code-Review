package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.IndexElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultCreateIndexPath extends AbstractPath implements CreateIndexPath {

    public DefaultCreateIndexPath() {
        super(null);
    }

    @Override
    public OnPath create(String indexName) {
        element(new IndexElement(indexName));
        return new DefaultOnPath(this);
    }

    @Override
    public OnPrimaryPath createPrimary() {
        element(new IndexElement());
        return new DefaultOnPrimaryPath(this);
    }
}
