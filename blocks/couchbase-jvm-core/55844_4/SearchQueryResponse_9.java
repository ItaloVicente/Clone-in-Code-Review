
package com.couchbase.client.core.message.search;


import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.BootstrapMessage;

public class SearchQueryRequest extends AbstractCouchbaseRequest implements SearchRequest {

    private final String indexName;
    private final String payload;

    public SearchQueryRequest(String indexName, String payload, String username, String password) {
        super(username, password);
        this.indexName = indexName;
        this.payload = payload;
    }

    @Override
    public String path() {
        return "/api/index/" + indexName + "/query";
    }

    public String payload() {
        return payload;
    }
}
