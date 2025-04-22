package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.DropIndexElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultDropPath extends AbstractPath implements DropPath {

    public DefaultDropPath() {
        super(null);
    }

    @Override
    public UsingPath drop(String keyspace, String indexName) {
        return drop(null, keyspace, indexName);
    }

    @Override
    public UsingPath drop(String namespace, String keyspace, String indexName) {
        element(new DropIndexElement(namespace, keyspace, indexName));
        return new DefaultUsingPath(this);
    }

    @Override
    public UsingPath dropPrimary(String keyspace) {
        return dropPrimary(null, keyspace);
    }

    @Override
    public UsingPath dropPrimary(String namespace, String keyspace) {
        element(new DropIndexElement(namespace, keyspace, null));
        return new DefaultUsingPath(this);
    }
}
