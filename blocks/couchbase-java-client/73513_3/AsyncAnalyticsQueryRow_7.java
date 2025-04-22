package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public interface AsyncAnalyticsQueryResult {

    Observable<AsyncAnalyticsQueryRow> rows();

    Observable<Object> signature();

    Observable<AnalyticsMetrics> info();

    boolean parseSuccess();

    Observable<String> status();

    Observable<Boolean> finalSuccess();

    Observable<JsonObject> errors();

    String requestId();

    String clientContextId();
}
