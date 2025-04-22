package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.CouchbaseResponse;

public class ResponseEvent {

    private CouchbaseResponse response;

    public ResponseEvent setResponse(final CouchbaseResponse response) {
        this.response = response;
        return this;
    }

    public CouchbaseResponse getResponse() {
        return response;
    }
}
