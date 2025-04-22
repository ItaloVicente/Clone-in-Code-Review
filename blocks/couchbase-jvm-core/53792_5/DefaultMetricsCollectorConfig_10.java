    public static DefaultMetricsCollectorConfig create(long emitFrequency, TimeUnit emitFrequencyUnit) {
        Builder builder = builder();
        builder.emitFrequency(emitFrequency);
        builder.emitFrequencyUnit(emitFrequencyUnit);
        return builder.build();
    }

