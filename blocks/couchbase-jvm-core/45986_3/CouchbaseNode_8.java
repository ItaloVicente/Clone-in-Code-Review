package com.couchbase.client.core.event.system;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;

import java.net.InetAddress;

public class NodeDisconnectedEvent implements CouchbaseEvent {

    private final InetAddress node;

    public NodeDisconnectedEvent(InetAddress node) {
        this.node = node;
    }

    @Override
    public EventType type() {
        return EventType.SYSTEM;
    }

    public InetAddress host() {
        return node;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NodeDisconnectedEvent{");
        sb.append("node=").append(node);
        sb.append('}');
        return sb.toString();
    }
}
