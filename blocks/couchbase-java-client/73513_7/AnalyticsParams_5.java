package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

import java.io.Serializable;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class AnalyticsMetrics implements Serializable {

    private static final long serialVersionUID = -1955101433651194843L;

    public static final AnalyticsMetrics EMPTY_METRICS = new AnalyticsMetrics();

    public static final String NO_TIME = "0s";

    private final JsonObject rawMetrics;

    private final int resultCount;
    private final int errorCount;
    private final int warningCount;
    private final int mutationCount;
    private final int sortCount;
    private final long resultSize;
    private final String elapsedTime;
    private final String executionTime;

    private AnalyticsMetrics() {
        this(JsonObject.empty());
    }

    public AnalyticsMetrics(JsonObject rawMetrics) {
        this.rawMetrics = rawMetrics;

        if (rawMetrics.getString("elapsedTime") == null) {
            this.elapsedTime = NO_TIME;
        } else {
            this.elapsedTime = rawMetrics.getString("elapsedTime");
        }

        if (rawMetrics.getString("executionTime") == null) {
            this.executionTime = NO_TIME;
        } else {
            this.executionTime = rawMetrics.getString("executionTime");
        }

        this.resultCount  = parseBestEffortToInt(rawMetrics, "resultCount");
        this.errorCount = parseBestEffortToInt(rawMetrics, "errorCount");
        this.warningCount = parseBestEffortToInt(rawMetrics, "warningCount");
        this.mutationCount = parseBestEffortToInt(rawMetrics, "mutationCount");
        this.sortCount = parseBestEffortToInt(rawMetrics, "sortCount");
        this.resultSize = parseBestEffortToLong(rawMetrics, "resultSize");
    }

    private int parseBestEffortToInt(JsonObject input, String fieldname) {
        Object value = input.get(fieldname);
        if (value == null) {
            return 0;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return Integer.parseInt(value.toString());
        }
    }

    private long parseBestEffortToLong(JsonObject input, String fieldname) {
        Object value = input.get(fieldname);
        if (value == null) {
            return 0;
        }

        if (value instanceof Long) {
            return (Long) value;
        } else {
            return Long.parseLong(value.toString());
        }
    }


    public String elapsedTime() {
        return elapsedTime;
    }

    public String executionTime() {
        return executionTime;
    }

    public int sortCount() {
        return sortCount;
    }

    public int resultCount() {
        return resultCount;
    }

    public long resultSize() {
        return resultSize;
    }

    public int mutationCount() {
        return mutationCount;
    }
    public int errorCount() {
        return errorCount;
    }

    public int warningCount() {
        return warningCount;
    }

    public JsonObject asJsonObject() {
        return rawMetrics;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnalyticsMetrics{");
        sb.append("resultCount=").append(resultCount);
        sb.append(", errorCount=").append(errorCount);
        sb.append(", warningCount=").append(warningCount);
        sb.append(", mutationCount=").append(mutationCount);
        sb.append(", sortCount=").append(sortCount);
        sb.append(", resultSize=").append(resultSize);
        sb.append(", elapsedTime='").append(elapsedTime).append('\'');
        sb.append(", executionTime='").append(executionTime).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
