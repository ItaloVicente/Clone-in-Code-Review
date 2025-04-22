package com.couchbase.client.core.event.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;
import com.couchbase.client.core.metrics.LatencyMetricsIdentifier;

import java.util.Map;

public abstract class LatencyMetricsEvent<I extends LatencyMetricsIdentifier> implements CouchbaseEvent {

    private Map<I, LatencyMetric> latencies;

    public LatencyMetricsEvent(Map<I, LatencyMetric> latencies) {
        this.latencies = latencies;
    }

    public Map<I, LatencyMetric> latencies() {
        return latencies;
    }

    @Override
    public EventType type() {
        return EventType.METRIC;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName() + "{");
        sb.append("latencies=").append(latencies);
        sb.append('}');
        return sb.toString();
    }
}
