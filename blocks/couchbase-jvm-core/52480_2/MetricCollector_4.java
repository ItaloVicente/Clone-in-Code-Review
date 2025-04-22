package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public class DefaultMetricCollectorConfig implements MetricCollectorConfig {

    @Override
    public int emitFrequency() {
        return 10;
    }

    @Override
    public TimeUnit emitFrequencyUnit() {
        return TimeUnit.SECONDS;
    }

    @Override
    public TimeUnit targetUnit() {
        return TimeUnit.MICROSECONDS;
    }
}
