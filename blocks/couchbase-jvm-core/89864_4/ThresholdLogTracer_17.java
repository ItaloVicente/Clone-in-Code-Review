    public static ThresholdLogTracer create() {
        return create(ThresholdLogReporter.create());
    }

    public static ThresholdLogTracer create(final ThresholdLogReporter reporter) {
        return new ThresholdLogTracer(reporter);
    }

