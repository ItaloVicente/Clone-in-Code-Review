        @Override
        public LatencyMetricCollector networkLatencyMetricCollector() {
            return null;
        }

        public Builder networkLatencyMetricCollectorConfig(MetricCollectorConfig config) {
            this.networkLatencyMetricCollectorConfig = config;
            return this;
        }

        public MetricCollectorConfig networkLatencyMetricCollectorConfig() {
            return networkLatencyMetricCollectorConfig;
        }

