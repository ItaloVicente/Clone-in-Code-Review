package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class RegexpQuery extends SearchQuery {

    private final String regexp;
    private String field;

    public RegexpQuery(String regexp, SearchParams searchParams) {
        super(searchParams);
        this.regexp = regexp;
    }

    public RegexpQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public RegexpQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("regexp", regexp);
        if (field != null) {
            input.put("field", field);
        }
    }
}
