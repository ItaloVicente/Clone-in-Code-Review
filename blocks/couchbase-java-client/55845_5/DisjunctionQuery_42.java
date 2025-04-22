
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class DisjunctionQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final SearchQuery[] disjuncts;
    private final double boost;

    protected DisjunctionQuery(Builder builder) {
        super(builder);
        disjuncts = builder.disjuncts;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SearchQuery[] disjuncts() {
        return disjuncts;
    }

    public double boost() {
        return boost;
    }

    @Override
    public JsonObject queryJson() {
        JsonArray disjunctsJson = JsonArray.create();
        for (SearchQuery disjunct : disjuncts) {
            disjunctsJson.add(disjunct.queryJson());
        }
        return JsonObject.create()
                .put("disjuncts", disjunctsJson)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private SearchQuery[] disjuncts;

        public DisjunctionQuery build() {
            return new DisjunctionQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder disjuncts(SearchQuery ...disjuncts) {
            this.disjuncts = disjuncts;
            return this;
        }
    }
}
