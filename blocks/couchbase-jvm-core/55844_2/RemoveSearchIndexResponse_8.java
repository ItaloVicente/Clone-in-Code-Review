
package com.couchbase.client.core.message.search;


import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.BootstrapMessage;

public class RemoveSearchIndexRequest extends AbstractCouchbaseRequest implements SearchRequest, BootstrapMessage {

    private final String indexName;

    public RemoveSearchIndexRequest(String indexName, String username, String password) {
        super(username, password);
        this.indexName = indexName;
    }

    @Override
    public String path() {
        return "/api/index/" + indexName;
    }
}
