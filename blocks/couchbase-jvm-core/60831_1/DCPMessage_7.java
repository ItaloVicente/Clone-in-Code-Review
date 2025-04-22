
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class BufferAcknowledgmentResponse extends AbstractDCPResponse {
    public BufferAcknowledgmentResponse(final ResponseStatus status, final CouchbaseRequest request) {
        super(status, request);
    }
}
