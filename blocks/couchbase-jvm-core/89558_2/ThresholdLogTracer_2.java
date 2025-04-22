        this(new ThresholdLogScopeManager(), ThresholdLogReporter.disabled());
    }

    private ThresholdLogTracer(final ThresholdLogScopeManager scopeManager,
        final ThresholdLogReporter reporter) {
        this.scopeManager = scopeManager;
        this.reporter = reporter;
