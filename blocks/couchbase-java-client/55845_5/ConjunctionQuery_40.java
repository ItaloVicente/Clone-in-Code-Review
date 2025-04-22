
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class ConjunctionQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final SearchQuery[] conjuncts;
    private final double boost;

    protected ConjunctionQuery(Builder builder) {
        super(builder);
        conjuncts = builder.conjuncts;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SearchQuery[] conjuncts() {
        return conjuncts;
    }

    public double boost() {
        return boost;
    }

    @Override
    public JsonObject queryJson() {
        JsonArray conjunctsJson = JsonArray.create();
        for (SearchQuery conjunct : conjuncts) {
            conjunctsJson.add(conjunct.queryJson());
        }
        return JsonObject.create()
                .put("conjuncts", conjunctsJson)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private SearchQuery[] conjuncts;

        public ConjunctionQuery build() {
            return new ConjunctionQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder conjuncts(SearchQuery ...conjuncts) {
            this.conjuncts = conjuncts;
            return this;
        }
    }
}
