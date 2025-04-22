
package com.couchbase.client.core.node.locate;

import com.couchbase.client.core.config.ClusterConfig;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.node.Node;

import java.util.Set;

public class DCPLocator implements Locator {
    @Override
    public Node[] locate(CouchbaseRequest request, Set<Node> nodes, ClusterConfig config) {
        if (nodes.isEmpty()) {
            return new Node[] {};
        } else {
            return new Node[]{nodes.iterator().next()};
        }
    }
}
