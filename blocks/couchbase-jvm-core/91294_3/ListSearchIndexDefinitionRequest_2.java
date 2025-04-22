
package com.couchbase.client.core.message.search;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.tracing.ThresholdLogReporter;
import io.opentracing.Span;
import io.opentracing.tag.Tags;

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
    protected void afterSpanSet(Span span) {
        span.setTag(Tags.PEER_SERVICE.getKey(), ThresholdLogReporter.SERVICE_FTS);
    }

    @Override
    public String path() {
        return "/api/index" + (this.indexName == null ? "" : "/" + this.indexName);
    }
}
