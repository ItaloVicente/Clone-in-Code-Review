package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;

public abstract class AbstractFtsQuery {

    private Double boost;

    protected AbstractFtsQuery() { }

    public AbstractFtsQuery boost(double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject export() {
        return export(null);
    }

    public JsonObject export(SearchParams searchParams) {
        JsonObject result = JsonObject.create();
        if (searchParams != null) {
            searchParams.injectParams(result);
        }
        JsonObject query = JsonObject.create();
        injectParamsAndBoost(query);
        return result.put("query", query);
    }

    public void injectParamsAndBoost(JsonObject input) {
        if (boost != null) {
            input.put("boost", boost);
        }
        injectParams(input);
    }

    protected abstract void injectParams(JsonObject input);

    @Override
    public String toString() {
        return export(null).toString();
    }

}
