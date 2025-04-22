
package com.couchbase.client.core.message.search;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.BootstrapMessage;

public class UpsertSearchIndexRequest extends AbstractCouchbaseRequest implements SearchRequest, BootstrapMessage {

    private final String indexName;
    private final String payload;

    public UpsertSearchIndexRequest(String indexName, String payload, String username, String password) {
        super(username, password);
        this.indexName = indexName;
        this.payload = payload;
    }

    @Override
    public String path() {
        return "/api/index/" + indexName;
    }

    public String payload() {
        return payload;
    }
}
