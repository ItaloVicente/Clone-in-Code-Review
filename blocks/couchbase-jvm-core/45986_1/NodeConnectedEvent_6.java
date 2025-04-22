
package com.couchbase.client.core.event.system;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;

public class BucketOpenedEvent implements CouchbaseEvent {

    private final String name;

    public BucketOpenedEvent(String name) {
        this.name = name;
    }

    @Override
    public EventType type() {
        return EventType.SYSTEM;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BucketOpenedEvent{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
