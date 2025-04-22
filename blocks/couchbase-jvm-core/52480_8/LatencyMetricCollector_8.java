package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public class DefaultMetricCollectorConfig implements MetricCollectorConfig {

    public static final int DEFAULT_EMIT_FREQUENCY = 30;
    public static final TimeUnit DEFAULT_EMIT_FREQUENCY_UNIT = TimeUnit.SECONDS;
    public static final TimeUnit DEFAULT_TARGET_UNIT = TimeUnit.MICROSECONDS;

    private final int emitFrequency;
    private final TimeUnit emitFrequencyUnit;
    private final TimeUnit targetUnit;

    public static DefaultMetricCollectorConfig create() {
        return new DefaultMetricCollectorConfig(builder());
    }

    public static Builder builder() {
        return new Builder();
    }

    protected DefaultMetricCollectorConfig(final Builder builder) {
        emitFrequency = builder.emitFrequency();
        emitFrequencyUnit = builder.emitFrequencyUnit();
        targetUnit = builder.targetUnit();
    }

    @Override
    public int emitFrequency() {
        return emitFrequency;
    }

    @Override
    public TimeUnit emitFrequencyUnit() {
        return emitFrequencyUnit;
    }

    @Override
    public TimeUnit targetUnit() {
        return targetUnit;
    }

    public static class Builder implements MetricCollectorConfig {

        private int emitFrequency = DEFAULT_EMIT_FREQUENCY;
        private TimeUnit emitFrequencyUnit = DEFAULT_EMIT_FREQUENCY_UNIT;
        private TimeUnit targetUnit = DEFAULT_TARGET_UNIT;

        @Override
        public int emitFrequency() {
            return emitFrequency;
        }

        public Builder emitFrequency(int emitFrequency) {
            this.emitFrequency = emitFrequency;
            return this;
        }

        @Override
        public TimeUnit emitFrequencyUnit() {
            return emitFrequencyUnit;
        }

        public Builder emitFrequencyUnit(final TimeUnit emitFrequencyUnit) {
            this.emitFrequencyUnit = emitFrequencyUnit;
            return this;
        }

        @Override
        public TimeUnit targetUnit() {
            return targetUnit;
        }

        public Builder targetUnit(final TimeUnit targetUnit) {
            this.targetUnit = targetUnit;
            return this;
        }

    }

}
