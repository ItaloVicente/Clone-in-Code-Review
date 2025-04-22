
package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public enum HashSide {

    PROBE("PROBE"),

    BUILD("BUILD");

    private final String value;

    HashSide(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
