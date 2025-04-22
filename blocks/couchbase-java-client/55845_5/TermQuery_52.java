
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class TermQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final String term;
    private final String field;
    private final double boost;

    protected TermQuery(Builder builder) {
        super(builder);
        term = builder.term;
        field = builder.field;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String term() {
        return term;
    }
    public String field() {
        return field;
    }

    public double boost() {
        return boost;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("term", term)
                .put("field", field)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private String term;
        private String field;

        public TermQuery build() {
            return new TermQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
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
