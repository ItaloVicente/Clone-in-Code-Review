package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class StringQuery extends SearchQuery {

    private final String query;

    public StringQuery(String query) {
        super();
        this.query = query;
    }

    @Override
    public StringQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("query", query);
    }
}
