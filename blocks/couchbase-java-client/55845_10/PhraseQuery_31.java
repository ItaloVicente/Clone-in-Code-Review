
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class PhraseQuery extends SearchQuery {

    private final String[] terms;
    private final String field;

    protected PhraseQuery(Builder builder) {
        super(builder);
        terms = builder.terms;
        field = builder.field;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public String[] terms() {
        return terms;
    }
    public String field() {
        return field;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("terms", JsonArray.from(terms))
                .put("field", field);
    }

    public static class Builder extends SearchQuery.Builder {
        private String[] terms;
        private String field;

        protected Builder(String index) {
            super(index);
        }

        public PhraseQuery build() {
            return new PhraseQuery(this);
        }

        public Builder terms(String ...terms) {
            this.terms = terms;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }
    }
}
