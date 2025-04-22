package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DropIndexElement implements Element {

    private final String fullKeyspace;
    private final String indexName;

    public DropIndexElement(String namespace, String keyspace, String indexName) {
        if (namespace == null) {
            this.fullKeyspace = ESCAPE_CHAR + keyspace + ESCAPE_CHAR;
        } else {
            this.fullKeyspace = ESCAPE_CHAR + namespace + "`:`" + keyspace + ESCAPE_CHAR;
        }
        this.indexName = indexName == null ? null : ESCAPE_CHAR + indexName + ESCAPE_CHAR;
    }

    @Override
    public String export() {
        if (indexName == null) {
            return "DROP PRIMARY INDEX ON " + fullKeyspace;
        }
        return "DROP INDEX " + fullKeyspace + "." + indexName;
    }
}
