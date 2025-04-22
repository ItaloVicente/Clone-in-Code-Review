package com.couchbase.client.core.metrics;

public interface MetricCollector {

    void recordLatency(MetricIdentifier identifier, long latency);

    MetricCollectorConfig config();

}
