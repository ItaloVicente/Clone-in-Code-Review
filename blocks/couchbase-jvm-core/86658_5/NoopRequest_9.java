
package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class PingResponse extends AbstractCouchbaseResponse {

    public PingResponse(final ResponseStatus status, final CouchbaseRequest request) {
        super(status, request);
    }

}
