
package com.couchbase.client.java.query.dsl.path;

import java.util.Objects;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.Element;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class HashJoinHintElement implements Element {

    private final HashSide side;

    public HashJoinHintElement(HashSide side) {
        Objects.requireNonNull(side);
        this.side = side;
    }

    @Override
    public String export() {
        return "USE HASH(" + this.side + ")";
    }
}
