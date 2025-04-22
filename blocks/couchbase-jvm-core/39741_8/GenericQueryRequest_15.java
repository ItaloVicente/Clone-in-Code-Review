package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class ListDesignDocumentsRequest extends AbstractCouchbaseRequest implements ConfigRequest {

    private final String path;

    public ListDesignDocumentsRequest(String bucket, String password) {
        super(bucket, password);
        this.path = "/pools/default/buckets/" + bucket + "/ddocs";
    }

    public String path() {
        return path;
    }
}
