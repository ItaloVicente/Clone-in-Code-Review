        @Override
        public Builder mutationTokensEnabled(boolean mutationTokensEnabled) {
            super.mutationTokensEnabled(mutationTokensEnabled);
            return this;
        }

        @Override
        public Builder tcpNodelayEnabled(boolean tcpNodelayEnabled) {
            super.tcpNodelayEnabled(tcpNodelayEnabled);
            return this;
        }

        @Override
        public Builder runtimeMetricsCollectorConfig(MetricsCollectorConfig metricsCollectorConfig) {
            super.runtimeMetricsCollectorConfig(metricsCollectorConfig);
            return this;
        }

        @Override
        public Builder networkLatencyMetricsCollectorConfig(LatencyMetricsCollectorConfig metricsCollectorConfig) {
            super.networkLatencyMetricsCollectorConfig(metricsCollectorConfig);
            return this;
        }

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

        @Override
        public Builder socketConnectTimeout(int socketConnectTimeout) {
            super.socketConnectTimeout(socketConnectTimeout);
            return this;
        }

        @Override
        public Builder callbacksOnIoPool(boolean callbacksOnIoPool) {
            super.callbacksOnIoPool(callbacksOnIoPool);
            return this;
        }

        @Override
        public Builder searchEndpoints(int searchEndpoints) {
            super.searchEndpoints(searchEndpoints);
            return this;
        }

        @Override
        public Builder sslKeystore(KeyStore sslKeystore) {
            super.sslKeystore(sslKeystore);
            return this;
        }

        @Override
        public Builder requestBufferWaitStrategy(WaitStrategyFactory waitStrategy) {
            super.requestBufferWaitStrategy(waitStrategy);
            return this;
        }

        @Override
        public Builder memcachedHashingStrategy(MemcachedHashingStrategy memcachedHashingStrategy) {
            super.memcachedHashingStrategy(memcachedHashingStrategy);
            return this;
        }

        @Override
        public Builder kvIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.kvIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder viewIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.viewIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder queryIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.queryIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder searchIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.searchIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder keyValueServiceConfig(KeyValueServiceConfig keyValueServiceConfig) {
            super.keyValueServiceConfig(keyValueServiceConfig);
            return this;
        }

        @Override
        public Builder viewServiceConfig(ViewServiceConfig viewServiceConfig) {
            super.viewServiceConfig(viewServiceConfig);
            return this;
        }

        @Override
        public Builder queryServiceConfig(QueryServiceConfig queryServiceConfig) {
            super.queryServiceConfig(queryServiceConfig);
            return this;
        }

        @Override
        public Builder searchServiceConfig(SearchServiceConfig searchServiceConfig) {
            super.searchServiceConfig(searchServiceConfig);
            return this;
        }

        @Override
        public Builder configPollInterval(long configPollInterval) {
            super.configPollInterval(configPollInterval);
            return this;
        }

        @Override
        public Builder certAuthEnabled(boolean certAuthEnabled) {
            super.certAuthEnabled(certAuthEnabled);
            return this;
        }

        @Override
        public Builder continuousKeepAliveEnabled(boolean continuousKeepAliveEnabled) {
            super.continuousKeepAliveEnabled(continuousKeepAliveEnabled);
            return this;
        }

        @Override
        public Builder keepAliveErrorThreshold(long keepAliveErrorThreshold) {
            super.keepAliveErrorThreshold(keepAliveErrorThreshold);
            return this;
        }

        @Override
        public Builder keepAliveTimeout(long keepAliveTimeout) {
            super.keepAliveTimeout(keepAliveTimeout);
            return this;
        }

        @Override
        public Builder couchbaseCoreSendHook(CouchbaseCoreSendHook hook) {
            super.couchbaseCoreSendHook(hook);
            return this;
        }

        @Override
        public Builder sslTruststoreFile(String sslTruststoreFile) {
            super.sslTruststoreFile(sslTruststoreFile);
            return this;
        }

        @Override
        public Builder sslTruststorePassword(String sslTruststorePassword) {
            super.sslTruststorePassword(sslTruststorePassword);
            return this;
        }

        @Override
        public Builder sslTruststore(KeyStore sslTruststore) {
            super.sslTruststore(sslTruststore);
            return this;
        }

        @Override
        public Builder configPollFloorInterval(long configPollFloorInterval) {
            super.configPollFloorInterval(configPollFloorInterval);
            return this;
        }

        @Override
        public Builder forceSaslPlain(boolean forceSaslPlain) {
            super.forceSaslPlain(forceSaslPlain);
            return this;
        }
