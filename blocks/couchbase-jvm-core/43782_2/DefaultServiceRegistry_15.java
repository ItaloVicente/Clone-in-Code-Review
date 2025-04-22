
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class StreamRequestResponse extends AbstractDCPResponse {
    public StreamRequestResponse(ResponseStatus status, CouchbaseRequest request) {
        super(status, request);
    }
}
