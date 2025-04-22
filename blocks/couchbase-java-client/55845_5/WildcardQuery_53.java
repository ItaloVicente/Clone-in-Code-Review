
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class WildcardQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final String wildcard;
    private final String field;
    private final double boost;

    protected WildcardQuery(Builder builder) {
        super(builder);
        wildcard = builder.wildcard;
        field = builder.field;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String wildcard() {
        return wildcard;
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
                .put("wildcard", wildcard)
                .put("field", field)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private String wildcard;
        private String field;

        public WildcardQuery build() {
            return new WildcardQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder wildcard(String wildcard) {
            this.wildcard = wildcard;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }
    }
}
