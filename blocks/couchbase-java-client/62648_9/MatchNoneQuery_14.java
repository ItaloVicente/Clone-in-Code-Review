package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class MatchAllQuery extends SearchQuery {

    public MatchAllQuery(SearchParams searchParams) {
        super(searchParams);
    }

    @Override
    public MatchAllQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("match_all", (String) null);
    }
}
