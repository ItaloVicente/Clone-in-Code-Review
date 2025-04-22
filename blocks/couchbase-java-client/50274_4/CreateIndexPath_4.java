package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class WithIndexOptionElement implements Element {

    private final JsonObject options;

    public WithIndexOptionElement(JsonObject options) {
        this.options = options;
    }

    @Override
    public String export() {
        return "WITH `" + options.toString() + "`";
    }
}
