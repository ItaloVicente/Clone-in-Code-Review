package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.query.dsl.element.WithIndexOptionElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultWithPath extends AbstractPath implements WithPath {

    protected DefaultWithPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public Statement with(JsonObject options) {
        element(new WithIndexOptionElement(options));
        return this;
    }
}
