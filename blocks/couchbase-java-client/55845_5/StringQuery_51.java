
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class StringQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final String query;
    private final double boost;

    protected StringQuery(Builder builder) {
        super(builder);
        query = builder.query;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String query() {
        return query;
    }

    public double boost() {
        return boost;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("query", query)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private String query;

        public StringQuery build() {
            return new StringQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }
    }
}
