package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Committed
@InterfaceAudience.Public
public interface AsyncAnalyticsQueryRow {

    byte[] byteValue();

    JsonObject value();
}
