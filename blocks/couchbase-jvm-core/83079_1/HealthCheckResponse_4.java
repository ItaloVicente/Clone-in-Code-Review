package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class HealthCheckRequest extends AbstractCouchbaseRequest implements InternalRequest {

    public HealthCheckRequest() {
        super(null, null);
    }

}
