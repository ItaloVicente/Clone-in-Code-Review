package com.couchbase.client.core.metrics;

public interface MetricsCollector {

    void recordLatency(MetricIdentifier identifier, long latency);


}
