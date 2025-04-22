package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class IndexElement implements Element {

    private final String name;

    public IndexElement(String indexName) {
        this.name = indexName;
    }

    public IndexElement() {
        this(null);
    }

    @Override
    public String export() {
        if (name == null) {
            return "CREATE PRIMARY INDEX";
        } else {
            return "CREATE INDEX " + name + "";
        }
    }
}
