package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class ParameterizedAnalyticsQuery extends SimpleAnalyticsQuery {

    private final JsonArray positional;
    private final JsonObject named;

    ParameterizedAnalyticsQuery(String statement, JsonArray positional, JsonObject named,
        AnalyticsParams params) {
        super(statement, params);
        this.named = named;
        this.positional = positional;
    }

    @Override
    public JsonObject query() {
        JsonObject query = super.query();

        if (named != null && !named.isEmpty()) {
            for (String key : named.getNames()) {
                Object value = named.get(key);
                if (!key.startsWith("$")) {
                    key = "$" + key;
                }
                query.put(key, value);
            }
        }
        if (positional != null && !positional.isEmpty()) {
            query.put("args", positional);
        }
        return query;
    }
}
