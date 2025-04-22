package com.couchbase.client.java.query;

import java.io.Serializable;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class QueryMetrics implements Serializable{

    private static final long serialVersionUID = -1955101433653293743L;

    public static final QueryMetrics EMPTY_METRICS = new QueryMetrics();

    public static final String NO_TIME = "0s";

    private final JsonObject rawMetrics;

    private final int resultCount;
    private final int errorCount;
    private final int warningCount;
    private final int mutationCount;
    private final long resultSize;
    private final String elapsedTime;
    private final String executionTime;

    private QueryMetrics() {
        this(JsonObject.empty());
    }

    public QueryMetrics(JsonObject rawMetrics) {
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

        Integer resultCount = rawMetrics.getInt("resultCount");
        this.resultCount = resultCount == null ? 0 : resultCount;

        Integer errorCount = rawMetrics.getInt("errorCount");
        this.errorCount = errorCount == null ? 0 : errorCount;

        Integer warningCount = rawMetrics.getInt("warningCount");
        this.warningCount = warningCount == null ? 0 : warningCount;

        Integer mutationCount = rawMetrics.getInt("mutationCount");
        this.mutationCount = mutationCount == null ? 0 : mutationCount;

        Long resultSize = rawMetrics.getLong("resultSize");
        this.resultSize = resultSize == null ? 0L : resultSize;
    }

    public String elapsedTime() {
        return elapsedTime;
    }

    public String executionTime() {
        return executionTime;
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
}
