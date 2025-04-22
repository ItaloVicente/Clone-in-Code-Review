package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class HealthCheckRequest extends AbstractCouchbaseRequest implements InternalRequest {

    private final boolean ping;

    public HealthCheckRequest(boolean ping) {
        super(null, null);
        this.ping = ping;
    }

    public boolean ping() {
        return ping;
    }
}
