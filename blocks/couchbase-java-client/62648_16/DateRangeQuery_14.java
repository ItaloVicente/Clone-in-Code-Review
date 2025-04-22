package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class ConjunctionQuery extends AbstractCompoundQuery {

    public ConjunctionQuery(AbstractFtsQuery... queries) {
        super(queries);
    }

    @Override
    public ConjunctionQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    public ConjunctionQuery and(AbstractFtsQuery... queries) {
        super.addAll(queries);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (childQueries.isEmpty()) {
            throw new IllegalArgumentException("Compound query has no child query");
        }

        JsonArray conjuncts = JsonArray.create();
        for (AbstractFtsQuery childQuery : childQueries) {
            JsonObject childJson = JsonObject.create();
            childQuery.injectParamsAndBoost(childJson);
            conjuncts.add(childJson);
        }
        input.put("conjuncts", conjuncts);
    }
}
