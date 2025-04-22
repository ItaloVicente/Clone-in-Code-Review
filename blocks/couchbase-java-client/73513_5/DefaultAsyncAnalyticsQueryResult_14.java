package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.TranscodingException;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class DefaultAnalyticsQueryRow implements AnalyticsQueryRow {


    private final AsyncAnalyticsQueryRow asyncRow;

    public DefaultAnalyticsQueryRow(AsyncAnalyticsQueryRow asyncRow) {
        this.asyncRow = asyncRow;
    }

    @Override
    public byte[] byteValue() {
        return asyncRow.byteValue();
    }

    @Override
    public JsonObject value() {
        return asyncRow.value();
    }

    @Override
    public String toString() {
        return value().toString();
    }
}
