package com.couchbase.client.core.event.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;

import java.util.Map;

public class SystemMetricsEvent implements CouchbaseEvent {

    private final Map<String, Object> info;

    public SystemMetricsEvent(Map<String, Object> info) {
        this.info = info;
    }

    public Map<String, Object> all() {
        return info;
    }

    @Override
    public EventType type() {
        return EventType.METRIC;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemMetricEvent");
        sb.append(info);
        return sb.toString();
    }


}
