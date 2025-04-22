package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

import java.io.Serializable;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public abstract class AnalyticsQuery implements Serializable {

    private static final long serialVersionUID = 3758113456237959730L;

    public abstract String statement();

    public abstract AnalyticsParams params();

    public abstract JsonObject query();

    public static SimpleAnalyticsQuery simple(final String statement) {
        return simple(statement, null);
    }

    public static SimpleAnalyticsQuery simple(final String statement, final AnalyticsParams params) {
        return new SimpleAnalyticsQuery(statement, params);
    }

}
