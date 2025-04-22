
package com.couchbase.client.core.endpoint.dcp;

public class DCPConnection {
    private final String name;
    private volatile int totalReceivedBytes;

    public DCPConnection(String name) {
        this.name = name;
        this.totalReceivedBytes = 0;
    }

    public String name() {
        return name;
    }

    public int totalReceivedBytes() {
        return totalReceivedBytes;
    }

    public void inc(int delta) {
        totalReceivedBytes += delta;
    }

    public void reset() {
        totalReceivedBytes = 0;
    }
}
