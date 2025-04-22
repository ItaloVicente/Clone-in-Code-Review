package com.couchbase.client.core.metrics;

import java.util.concurrent.TimeUnit;

public class DefaultLatencyMetricsCollectorConfig
    extends DefaultMetricsCollectorConfig
    implements LatencyMetricsCollectorConfig {

    public static final TimeUnit TARGET_UNIT = TimeUnit.MICROSECONDS;
    public static final Double[] TARGET_PERCENTILES = new Double[] { 50.0, 90.0, 95.0, 99.0, 99.9 };

    private final TimeUnit targetUnit;
    private final Double[] targetPercentiles;

    public static DefaultLatencyMetricsCollectorConfig create() {
        return new DefaultLatencyMetricsCollectorConfig(builder());
    }

    public static Builder builder() {
        return new Builder();
    }

    private DefaultLatencyMetricsCollectorConfig(Builder builder) {
        super(builder);

        this.targetUnit = builder.targetUnit;
        this.targetPercentiles = builder.targetPercentiles;
    }

    @Override
    public TimeUnit targetUnit() {
        return targetUnit;
    }

    @Override
    public Double[] targetPercentiles() {
        return targetPercentiles;
    }

    public static class Builder extends DefaultMetricsCollectorConfig.Builder {

        private TimeUnit targetUnit = TARGET_UNIT;
        private Double[] targetPercentiles = TARGET_PERCENTILES;

        protected Builder() {
        }

        public Builder targetUnit(TimeUnit targetUnit) {
            this.targetUnit = targetUnit;
            return this;
        }

        public Builder targetPercentiles(Double[] targetPercentiles) {
            this.targetPercentiles = targetPercentiles;
            return this;
        }

        @Override
        public Builder emitFrequency(long emitFrequency) {
            super.emitFrequency(emitFrequency);
            return this;
        }

        @Override
        public Builder emitFrequencyUnit(TimeUnit emitFrequencyUnit) {
            super.emitFrequencyUnit(emitFrequencyUnit);
            return this;
        }

        public DefaultLatencyMetricsCollectorConfig build() {
            return new DefaultLatencyMetricsCollectorConfig(this);
        }
    }

}
