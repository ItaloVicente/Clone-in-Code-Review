
package com.couchbase.client.core.message.dcp;

public enum ConnectionType {
    CONSUMER(0),
    PRODUCER(1),
    NOTIFIER(2);
    private final int flags;

    private ConnectionType(int flags) {
        this.flags = flags;
    }

    public int flags() {
        return flags;
    }
}
