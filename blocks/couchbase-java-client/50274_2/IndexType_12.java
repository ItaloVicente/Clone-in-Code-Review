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

    public DefaultWithPath(AbstractPath parent) {
        super(parent);
    }

    private Statement with(boolean defer, String nodeName) {
        JsonObject options = JsonObject.create();
        if (defer) {
            options.put("defer_build", true);
        }
        if (nodeName != null) {
            options.put("nodes", nodeName);
        }
        element(new WithIndexOptionElement(options));
        return this;
    }

    @Override
    public Statement withNode(String nodeName) {
        return with(false, nodeName);
    }

    @Override
    public Statement withDefer() {
        return with(true, null);
    }

    @Override
    public Statement withDeferAndNode(String nodeName) {
        return with(true, nodeName);
    }
}
