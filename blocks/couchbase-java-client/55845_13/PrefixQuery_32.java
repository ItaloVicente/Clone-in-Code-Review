
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class PrefixQuery extends SearchQuery {
    private final String prefix;
    private final String field;

    protected PrefixQuery(Builder builder) {
        super(builder);
        prefix = builder.prefix;
        field = builder.field;
    }

    public static Builder on(String index) {
        return new Builder(index);
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
                .put("field", field);
    }

    public static class Builder extends SearchQuery.Builder {
        private String prefix;
        private String field;

        protected Builder(String index) {
            super(index);
        }

        public PrefixQuery build() {
            return new PrefixQuery(this);
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
