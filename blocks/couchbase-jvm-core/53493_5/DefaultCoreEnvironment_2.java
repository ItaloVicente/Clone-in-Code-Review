        this.networkLatencyMetricsCollector = new NetworkLatencyMetricsCollector(
            eventBus,
            coreScheduler,
            builder.networkLatencyMetricsCollectorConfig == null
                ? DefaultLatencyMetricsCollectorConfig.create()
                : builder.networkLatencyMetricsCollectorConfig
        );
