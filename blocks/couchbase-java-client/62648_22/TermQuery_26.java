package com.couchbase.client.java.fts.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class StringQuery extends AbstractFtsQuery {

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
