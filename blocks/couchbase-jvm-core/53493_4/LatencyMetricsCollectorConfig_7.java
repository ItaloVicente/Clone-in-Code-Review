
package com.couchbase.client.core.metrics;

public interface LatencyMetricsCollector<I extends LatencyMetricsIdentifier> extends MetricsCollector {

    void record(I identifier, long latency);

    @Override
    LatencyMetricsCollectorConfig config();
}
