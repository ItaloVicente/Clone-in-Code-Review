
package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.Element;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class HashJoinHintElement implements Element {

    private final HashSide side;

    public HashJoinHintElement(HashSide side) {
        this.side = side;
    }

    @Override
    public String export() {
        StringBuilder sb = new StringBuilder();
        sb.append("USE HASH(");
        sb.append(this.side.toString());
        sb.append(")");
        return sb.toString();
    }
}
