
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class AddStreamResponse extends AbstractDCPResponse {
    private final int streamIdentifier;

    public AddStreamResponse(ResponseStatus status, int streamIdentifier, CouchbaseRequest request) {
        super(status, request);
        this.streamIdentifier = streamIdentifier;
    }

    public int streamIdentifier() {
        return streamIdentifier;
    }
}
