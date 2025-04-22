
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class ConjunctionQuery extends SearchQuery {
    private final SearchQuery[] conjuncts;

    protected ConjunctionQuery(Builder builder) {
        super(builder);
        conjuncts = builder.conjuncts;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public SearchQuery[] conjuncts() {
        return conjuncts;
    }

    @Override
    public JsonObject queryJson() {
        JsonArray conjunctsJson = JsonArray.create();
        for (SearchQuery conjunct : conjuncts) {
            conjunctsJson.add(conjunct.queryJson());
        }
        return JsonObject.create()
                .put("conjuncts", conjunctsJson);
    }

    public static class Builder extends SearchQuery.Builder {
        private SearchQuery[] conjuncts;

        protected Builder(String index) {
            super(index);
        }

        public ConjunctionQuery build() {
            return new ConjunctionQuery(this);
        }

        public Builder conjuncts(SearchQuery... conjuncts) {
            this.conjuncts = conjuncts;
            return this;
        }
    }
}
