package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public class DefaultMetricsCollectorConfig implements MetricsCollectorConfig {

    public static final long EMIT_FREQUENCY = 1;
    public static final TimeUnit EMIT_FREQUENCY_UNIT = TimeUnit.HOURS;

    private final long emitFrequency;
    private final TimeUnit emitFrequencyUnit;

    public static DefaultMetricsCollectorConfig create() {
        return new DefaultMetricsCollectorConfig(builder());
    }

    public static Builder builder() {
        return new Builder();
    }

    private DefaultMetricsCollectorConfig(Builder builder) {
        emitFrequency = builder.emitFrequency;
        emitFrequencyUnit = builder.emitFrequencyUnit;
    }

    @Override
    public long emitFrequency() {
        return emitFrequency;
    }

    @Override
    public TimeUnit emitFrequencyUnit() {
        return emitFrequencyUnit;
    }

    public static class Builder {

        private long emitFrequency = EMIT_FREQUENCY;
        private TimeUnit emitFrequencyUnit = EMIT_FREQUENCY_UNIT;

        protected Builder() {
        }

        public Builder emitFrequency(long emitFrequency) {
            this.emitFrequency = emitFrequency;
            return this;
        }

        public Builder emitFrequencyUnit(TimeUnit emitFrequencyUnit) {
            this.emitFrequencyUnit = emitFrequencyUnit;
            return this;
        }

        public DefaultMetricsCollectorConfig build() {
            return new DefaultMetricsCollectorConfig(this);
        }

    }

}
