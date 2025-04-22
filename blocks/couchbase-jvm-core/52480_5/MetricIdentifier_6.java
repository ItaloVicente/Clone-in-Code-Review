
package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public interface MetricCollectorConfig {

    int emitFrequency();

    TimeUnit emitFrequencyUnit();

    TimeUnit targetUnit();

}
