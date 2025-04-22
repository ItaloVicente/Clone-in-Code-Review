package com.couchbase.client.java.fts.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class BooleanFieldQuery extends AbstractFtsQuery {

    private final boolean value;
    private String field;

    public BooleanFieldQuery(boolean value) {
        super();
        this.value = value;
    }

    public BooleanFieldQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public BooleanFieldQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (field != null) {
            input.put("field", field);
        }

        input.put("bool", value);
    }
}
