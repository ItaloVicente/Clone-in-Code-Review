
package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class NoopResponse extends AbstractKeyValueResponse {

    public NoopResponse(final ResponseStatus status, final short serverStatusCode, final CouchbaseRequest request) {
        super(status, serverStatusCode, null, null, request);
    }

}
