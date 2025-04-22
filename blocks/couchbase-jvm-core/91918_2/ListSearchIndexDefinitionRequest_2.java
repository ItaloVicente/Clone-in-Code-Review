
package com.couchbase.client.core.message.search;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class ListSearchIndexDefinitionRequest  extends AbstractCouchbaseRequest implements SearchRequest {
    private final String indexName;

    public ListSearchIndexDefinitionRequest(String username, String password) {
        this(username, password, null);
    }

    public ListSearchIndexDefinitionRequest(String username, String password, String indexName) {
        super(null, username, password);
        this.indexName = indexName;
    }

    @Override
    public String path() {
        return "/api/index" + (this.indexName == null ? "" : "/" + this.indexName);
    }
}
