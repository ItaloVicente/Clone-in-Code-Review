package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseResponse;

public class AddNodeResponse extends AbstractCouchbaseResponse implements CouchbaseResponse {

    private final String hostname;

    public AddNodeResponse(String hostname) {
        this.hostname = hostname;
    }

    public String hostname() {
        return hostname;
    }
}
