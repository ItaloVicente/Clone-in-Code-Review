package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;

public class MatchAllQuery extends AbstractFtsQuery {

    public MatchAllQuery() {
        super();
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
