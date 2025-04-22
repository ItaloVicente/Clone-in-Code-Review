        this.networkLatencyMetricCollector = new CoreNetworkLatencyMetricCollector(
            eventBus(),
            scheduler(),
            builder.networkLatencyMetricCollectorConfig() == null
                ? DefaultMetricCollectorConfig.create() : builder.networkLatencyMetricCollectorConfig()
        );
