
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class PrefixQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final String prefix;
    private final String field;
    private final double boost;

    protected PrefixQuery(Builder builder) {
        super(builder);
        prefix = builder.prefix;
        field = builder.field;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String prefix() {
        return prefix;
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
                .put("prefix", prefix)
                .put("field", field)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private String prefix;
        private String field;

        public PrefixQuery build() {
            return new PrefixQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }
    }
}
