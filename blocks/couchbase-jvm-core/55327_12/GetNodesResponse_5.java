
package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.service.ServiceType;

public class GetNodesRequest extends AbstractCouchbaseRequest implements InternalRequest {
    private final ServiceType type;

    public GetNodesRequest(ServiceType type, String bucket) {
        super(bucket, null);
        this.type = type;
    }

    public ServiceType type() {
        return type;
    }
}
