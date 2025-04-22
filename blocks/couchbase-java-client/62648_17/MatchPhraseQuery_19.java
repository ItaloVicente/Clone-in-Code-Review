package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;

public class MatchNoneQuery extends AbstractFtsQuery {

    public MatchNoneQuery() {
        super();
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
