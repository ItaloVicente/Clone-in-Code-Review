package com.couchbase.client.core.event.metrics;

import java.util.Map;

public class LatencyMetric {

    private final long min;
    private final long max;
    private final long count;
    private final Map<Double, Long> percentiles;

    public LatencyMetric(long min, long max, long count, Map<Double, Long> percentiles) {
        this.min = min;
        this.max = max;
        this.count = count;
        this.percentiles = percentiles;
    }

    public long min() {
        return min;
    }

    public long max() {
        return max;
    }

    public long count() {
        return count;
    }

    public Map<Double, Long> percentiles() {
        return percentiles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LatencyMetric{");
        sb.append("min=").append(min);
        sb.append(", max=").append(max);
        sb.append(", count=").append(count);
        sb.append(", percentiles=").append(percentiles);
        sb.append('}');
        return sb.toString();
    }
}
