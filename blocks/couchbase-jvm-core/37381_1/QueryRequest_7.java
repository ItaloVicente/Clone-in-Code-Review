package com.couchbase.client.core.message.query;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;

public class GenericQueryResponse extends AbstractCouchbaseResponse {

    private final String content;

    public GenericQueryResponse(String content, ResponseStatus status) {
        super(status);
        this.content = content;
    }

    public String content() {
        return content;
    }


}
