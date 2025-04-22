package com.couchbase.client.java.fts.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PrefixQuery extends AbstractFtsQuery {

    private final String prefix;
    private String field;

    public PrefixQuery(String prefix) {
        super();
        this.prefix = prefix;
    }

    public PrefixQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public PrefixQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("prefix", this.prefix);
        if (field != null) {
            input.put("field", field);
        }
    }
}
