package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.Index;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class IndexReference {

    private String indexReference;

    private IndexReference(String representation) {
        this.indexReference = representation;
    }

    @Override
    public String toString() {
        return indexReference;
    }

    public static final IndexReference indexRef(String indexName) {
        return indexRef(indexName, null);
    }

    public static final IndexReference indexRef(String indexName, IndexType type) {
        if (type == null) {
            return new IndexReference("`" + indexName + "`");
        }
        return new IndexReference("`" + indexName + "` USING " + type.toString());
    }
}
