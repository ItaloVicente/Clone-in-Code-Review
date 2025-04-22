package com.couchbase.client.java.query;

import com.couchbase.client.java.document.json.JsonObject;

public class QueryPlan implements Statement {

    private final JsonObject jsonPlan;

    public QueryPlan(JsonObject jsonPlan) {
        this .jsonPlan = jsonPlan;
    }

    @Override
    public String toString() {
        return jsonPlan.toString();
    }

    public JsonObject plan() {
        return jsonPlan;
    }
}
