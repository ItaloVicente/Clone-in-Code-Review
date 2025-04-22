package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class DisjunctionQuery extends AbstractCompoundQuery {

    private int min = 1;

    public DisjunctionQuery(SearchParams searchParams, SearchQuery... queries) {
        super(searchParams, queries);
    }

    @Override
    public DisjunctionQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    public DisjunctionQuery min(int min) {
        this.min = min;
        return this;
    }

    public DisjunctionQuery or(SearchQuery... queries) {
        super.addAll(queries);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (childQueries.isEmpty()) {
            throw new IllegalArgumentException("Compound query has no child query");
        }
        if (childQueries.size() < min) {
            throw new IllegalArgumentException("Disjunction query as fewer children than the configured minimum " + min);
        }

        input.put("min", min);

        JsonArray disjuncts = JsonArray.create();
        for (SearchQuery childQuery : childQueries) {
            JsonObject childJson = JsonObject.create();
            childQuery.injectParamsAndBoost(childJson);
            disjuncts.add(childJson);
        }
        input.put("disjuncts", disjuncts);
    }
}
