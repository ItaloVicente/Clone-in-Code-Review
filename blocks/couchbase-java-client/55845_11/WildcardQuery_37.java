
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class WildcardQuery extends SearchQuery {
    private final String wildcard;
    private final String field;

    protected WildcardQuery(Builder builder) {
        super(builder);
        wildcard = builder.wildcard;
        field = builder.field;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public String wildcard() {
        return wildcard;
    }
    public String field() {
        return field;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("wildcard", wildcard)
                .put("field", field);
    }

    public static class Builder extends SearchQuery.Builder {
        private String wildcard;
        private String field;

        protected Builder(String index) {
            super(index);
        }

        public WildcardQuery build() {
            return new WildcardQuery(this);
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
