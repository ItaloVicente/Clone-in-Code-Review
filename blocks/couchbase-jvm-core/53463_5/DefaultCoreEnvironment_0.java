        this.systemMetricsCollector = new SystemMetricsCollector(
            eventBus,
            coreScheduler,
            builder.systemMetricsCollectorConfig == null
                ? DefaultMetricsCollectorConfig.create()
                : builder.systemMetricsCollectorConfig
        );
