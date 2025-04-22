package com.couchbase.client.core.metrics;

public interface LatencyMetricCollector extends MetricCollector {

    void recordLatency(LatencyMetricIdentifier identifier, long latency);

}
