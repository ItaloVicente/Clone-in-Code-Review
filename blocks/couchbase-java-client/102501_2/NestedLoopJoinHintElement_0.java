package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class NestedLoopJoinHintElement implements Element {

    @Override
    public String export() {
        return "USE NL";
    }
}
