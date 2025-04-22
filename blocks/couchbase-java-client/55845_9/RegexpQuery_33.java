
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class RegexpQuery extends SearchQuery {
    private final String regexp;
    private final String field;

    protected RegexpQuery(Builder builder) {
        super(builder);
        regexp = builder.regexp;
        field = builder.field;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public String regexp() {
        return regexp;
    }
    public String field() {
        return field;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("regexp", regexp)
                .put("field", field);
    }

    public static class Builder extends SearchQuery.Builder {
        private String regexp;
        private String field;

        protected Builder(String index) {
            super(index);
        }

        public RegexpQuery build() {
            return new RegexpQuery(this);
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
