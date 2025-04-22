package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

import java.util.Iterator;
import java.util.List;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public interface AnalyticsQueryResult extends Iterable<AnalyticsQueryRow> {

    List<AnalyticsQueryRow> allRows();

    Iterator<AnalyticsQueryRow> rows();

    Object signature();

    AnalyticsMetrics info();

    boolean parseSuccess();

    boolean finalSuccess();

    String status();

    List<JsonObject> errors();

    String requestId();

    String clientContextId();

}
