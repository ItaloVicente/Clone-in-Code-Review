package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class MatchNoneQuery extends SearchQuery {

    public MatchNoneQuery(SearchParams searchParams) {
        super(searchParams);
    }

    @Override
    public MatchNoneQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("match_none", (String) null);
    }
}
