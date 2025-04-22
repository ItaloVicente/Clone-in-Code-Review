package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class BuildIndexElement implements Element {

    private final String fullKeyspace;

    public BuildIndexElement(String namespace, String keyspace) {
        if (namespace == null) {
            this.fullKeyspace = ESCAPE_CHAR + keyspace + ESCAPE_CHAR;
        } else {
            this.fullKeyspace = ESCAPE_CHAR + namespace + "`:`" + keyspace + ESCAPE_CHAR;
        }
    }

    @Override
    public String export() {
        return "BUILD INDEX ON " + fullKeyspace;
    }
}
