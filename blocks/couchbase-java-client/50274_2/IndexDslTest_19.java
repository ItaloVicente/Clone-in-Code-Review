package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.query.dsl.element.WithIndexOptionElement;
import com.couchbase.client.java.query.dsl.path.Path;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface WithPath extends Path, Statement {

    Statement withNode(String nodeName);

    Statement withDefer();

    Statement withDeferAndNode(String nodeName);

}
