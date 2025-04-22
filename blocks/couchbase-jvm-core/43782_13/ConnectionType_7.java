
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public abstract class AbstractDCPResponse extends AbstractCouchbaseResponse implements DCPResponse {
    public AbstractDCPResponse(ResponseStatus status, CouchbaseRequest request) {
        super(status, request);
    }
}
