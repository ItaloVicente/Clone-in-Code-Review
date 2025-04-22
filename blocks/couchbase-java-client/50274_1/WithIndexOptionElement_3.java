package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.path.index.IndexType;

@InterfaceStability.Experimental
public class UsingElement implements Element {
    private final IndexType type;

    public UsingElement(IndexType type) {
        this.type = type;
    }

    @Override
    public String export() {
        return "USING " + type.name();
    }
}
