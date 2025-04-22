package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class IndexNamesElement implements Element {

    private final String indexName;
    private final String[] otherNames;

    public IndexNamesElement(String indexName, String... indexNames) {
        this.indexName = indexName;
        this.otherNames = indexNames;
    }

    @Override
    public String export() {
        StringBuilder sb = new StringBuilder("(`").append(indexName).append('`');
        for (String otherName : otherNames) {
            sb.append(", `").append(otherName).append('`');
        }
        sb.append(')');
        return sb.toString();
    }
}
