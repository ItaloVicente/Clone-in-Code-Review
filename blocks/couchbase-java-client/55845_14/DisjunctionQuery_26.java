
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class DisjunctionQuery extends SearchQuery {
    private final SearchQuery[] disjuncts;

    protected DisjunctionQuery(Builder builder) {
        super(builder);
        disjuncts = builder.disjuncts;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public SearchQuery[] disjuncts() {
        return disjuncts;
    }

    @Override
    public JsonObject queryJson() {
        JsonArray disjunctsJson = JsonArray.create();
        for (SearchQuery disjunct : disjuncts) {
            disjunctsJson.add(disjunct.queryJson());
        }
        return JsonObject.create()
                .put("disjuncts", disjunctsJson);
    }

    public static class Builder extends SearchQuery.Builder {
        private SearchQuery[] disjuncts;

        protected Builder(String index) {
            super(index);
        }

        public DisjunctionQuery build() {
            return new DisjunctionQuery(this);
        }

        public Builder disjuncts(SearchQuery ...disjuncts) {
            this.disjuncts = disjuncts;
            return this;
        }
    }
}
