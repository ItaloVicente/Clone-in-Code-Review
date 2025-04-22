        @Override
        public Builder defaultMetricsLoggingConsumer(boolean enabled, CouchbaseLogLevel level, LoggingConsumer.OutputFormat format) {
            super.defaultMetricsLoggingConsumer(enabled, level, format);
            return this;
        }

        @Override
        public Builder defaultMetricsLoggingConsumer(boolean enabled, CouchbaseLogLevel level) {
            super.defaultMetricsLoggingConsumer(enabled, level);
            return this;
        }

