
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class StringQuery extends SearchQuery {
    private final String query;

    protected StringQuery(Builder builder) {
        super(builder);
        query = builder.query;
    }

    public static Builder on(String index) {
        return new Builder(index);
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
                .put("query", query);
    }

    public static class Builder extends SearchQuery.Builder {
        private String query;

        protected Builder(String index) {
            super(index);
        }

        public StringQuery build() {
            return new StringQuery(this);
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }
    }
}
