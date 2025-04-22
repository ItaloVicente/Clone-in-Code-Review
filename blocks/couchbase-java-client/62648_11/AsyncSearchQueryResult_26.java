package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class WildcardQuery extends SearchQuery {

    private final String wildcard;
    private String field;

    public WildcardQuery(String wildcard) {
        super();
        this.wildcard = wildcard;
    }

    public WildcardQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public WildcardQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (field != null) {
            input.put("field", field);
        }
        input.put("wildcard", wildcard);
    }
}
