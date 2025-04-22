package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class BooleanFieldQuery extends SearchQuery {

    private final boolean value;
    private String field;

    public BooleanFieldQuery(boolean value, SearchParams searchParams) {
        super(searchParams);
        this.value = value;
    }

    public BooleanFieldQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public BooleanFieldQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (field != null) {
            input.put("field", field);
        }

        input.put("bool", value);
    }
}
