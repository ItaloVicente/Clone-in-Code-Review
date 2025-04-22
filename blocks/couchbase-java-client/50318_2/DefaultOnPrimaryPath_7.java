package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.dsl.element.IndexNamesElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultIndexNamesPath extends AbstractPath implements IndexNamesPath {

    protected DefaultIndexNamesPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public UsingPath indexes(String indexName, String... indexNames) {
        element(new IndexNamesElement(indexName, indexNames));
        return new DefaultUsingPath(this);
    }

    @Override
    public UsingPath primary() {
        element(new IndexNamesElement(Index.PRIMARY_NAME));
        return new DefaultUsingPath(this);
    }
}
