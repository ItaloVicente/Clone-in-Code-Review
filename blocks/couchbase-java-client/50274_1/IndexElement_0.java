package com.couchbase.client.java.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.path.index.DefaultCreateIndexPath;
import com.couchbase.client.java.query.dsl.path.index.OnPath;
import com.couchbase.client.java.query.dsl.path.index.OnPrimaryPath;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class Index {

    public static OnPath createIndex(String indexName) {
        return new DefaultCreateIndexPath().create(indexName);
    }

    public static OnPrimaryPath createPrimaryIndex() {
        return new DefaultCreateIndexPath().createPrimary();
    }
}
