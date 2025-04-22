        public Builder defaultMetricsLoggingConsumer(boolean enabled, CouchbaseLogLevel level, LoggingConsumer.OutputFormat format) {
            if (enabled) {
                defaultMetricsLoggingConsumer = LoggingConsumer.create(level, format);
            } else {
                defaultMetricsLoggingConsumer = null;
            }
            return this;
        }

        public Builder defaultMetricsLoggingConsumer(boolean enabled, CouchbaseLogLevel level) {
            return defaultMetricsLoggingConsumer(enabled, level, LoggingConsumer.DEFAULT_FORMAT);
        }

