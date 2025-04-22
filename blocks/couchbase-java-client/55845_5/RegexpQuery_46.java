
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class RegexpQuery extends SearchQuery {
    public static final double BOOST = 1.0;

    private final String regexp;
    private final String field;
    private final double boost;

    protected RegexpQuery(Builder builder) {
        super(builder);
        regexp = builder.regexp;
        field = builder.field;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String regexp() {
        return regexp;
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
                .put("regexp", regexp)
                .put("field", field)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private String regexp;
        private String field;

        public RegexpQuery build() {
            return new RegexpQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder regexp(String regexp) {
            this.regexp = regexp;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }
    }
}
