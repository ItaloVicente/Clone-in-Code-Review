
package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class GetUsersResponse extends AbstractCouchbaseResponse {
    private final String content;

    public GetUsersResponse(String content, ResponseStatus status, CouchbaseRequest request) {
        super(status, request);
        this.content = content;
    }

    public String content() {
        return content;
    }
}
