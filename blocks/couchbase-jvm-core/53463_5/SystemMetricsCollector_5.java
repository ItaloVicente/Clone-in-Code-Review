package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public interface MetricsCollectorConfig {

    long emitFrequency();

    TimeUnit emitFrequencyUnit();

}
