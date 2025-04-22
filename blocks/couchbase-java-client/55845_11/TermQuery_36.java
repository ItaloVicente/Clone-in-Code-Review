
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class TermQuery extends SearchQuery {
    private final String term;
    private final String field;

    protected TermQuery(Builder builder) {
        super(builder);
        term = builder.term;
        field = builder.field;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public String term() {
        return term;
    }
    public String field() {
        return field;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("term", term)
                .put("field", field);
    }

    public static class Builder extends SearchQuery.Builder {
        private String term;
        private String field;

        protected Builder(String index) {
            super(index);
        }

        public TermQuery build() {
            return new TermQuery(this);
        }

        public Builder term(String term) {
            this.term = term;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }
    }
}
