package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class DefaultAnalyticsQueryResult implements AnalyticsQueryResult {

    private final String status;
    private final boolean finalSuccess;
    private final boolean parseSuccess;
    private final List<AnalyticsQueryRow> allRows;
    private final Object signature;
    private final AnalyticsMetrics info;
    private final List<JsonObject> errors;
    private final String requestId;
    private final String clientContextId;


    public DefaultAnalyticsQueryResult(List<AsyncAnalyticsQueryRow> rows, Object signature,
        AnalyticsMetrics info, List<JsonObject> errors,
        String finalStatus, Boolean finalSuccess, boolean parseSuccess,
        String requestId, String clientContextId) {

        this.requestId = requestId;
        this.clientContextId = clientContextId;
        this.parseSuccess = parseSuccess;
        this.finalSuccess = finalSuccess != null && finalSuccess;
        this.status = finalStatus;
        this.allRows = new ArrayList<AnalyticsQueryRow>(rows.size());
        for (AsyncAnalyticsQueryRow row : rows) {
            this.allRows.add(new DefaultAnalyticsQueryRow(row));
        }
        this.signature = signature;
        this.errors = errors;
        this.info = info;
    }

    @Override
    public List<AnalyticsQueryRow> allRows() {
        return this.allRows;
    }

    @Override
    public Iterator<AnalyticsQueryRow> rows() {
        return this.allRows.iterator();
    }

    @Override
    public Object signature() {
        return this.signature;
    }

    @Override
    public AnalyticsMetrics info() {
        return this.info;
    }

    @Override
    public boolean parseSuccess() {
        return this.parseSuccess;
    }

    @Override
    public List<JsonObject> errors() {
        return this.errors;
    }

    @Override
    public boolean finalSuccess() {
        return this.finalSuccess;
    }

    @Override
    public String status() {
        return status;
    }

    @Override
    public Iterator<AnalyticsQueryRow> iterator() {
        return rows();
    }

    @Override
    public String requestId() {
        return this.requestId;
    }

    @Override
    public String clientContextId() {
        return this.clientContextId;
    }
}
