
package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public interface LatencyMetricsCollectorConfig extends MetricsCollectorConfig {

    TimeUnit targetUnit();

    Double[] targetPercentiles();
}
