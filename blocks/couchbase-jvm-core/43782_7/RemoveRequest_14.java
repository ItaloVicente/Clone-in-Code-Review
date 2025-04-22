
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class OpenConnectionResponse extends AbstractDCPResponse {
    public OpenConnectionResponse(ResponseStatus status, CouchbaseRequest request) {
        super(status, request);
    }
}
