package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;

public class ListDesignDocumentResponse extends AbstractCouchbaseResponse {

    private final String content;

    public ListDesignDocumentResponse(String content, ResponseStatus status, CouchbaseRequest request) {
        super(status, request);
        this.content = content;
    }

    public String content() {
        return content;
    }

}
