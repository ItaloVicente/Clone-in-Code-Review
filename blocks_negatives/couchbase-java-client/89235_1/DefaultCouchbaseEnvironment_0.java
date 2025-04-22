        public Builder disconnectTimeout(long disconnectTimeout) {
            super.disconnectTimeout(disconnectTimeout);
            return this;
        }

        @Override
        public Builder sslEnabled(final boolean sslEnabled) {
            super.sslEnabled(sslEnabled);
            return this;
        }

        @Override
        public Builder sslKeystoreFile(final String sslKeystoreFile) {
            super.sslKeystoreFile(sslKeystoreFile);
            return this;
        }

        @Override
        public Builder sslKeystorePassword(final String sslKeystorePassword) {
            super.sslKeystorePassword(sslKeystorePassword);
            return this;
        }

        @Override
        public Builder bootstrapHttpEnabled(final boolean bootstrapHttpEnabled) {
            super.bootstrapHttpEnabled(bootstrapHttpEnabled);
            return this;
        }

        @Override
        public Builder bootstrapCarrierEnabled(final boolean bootstrapCarrierEnabled) {
            super.bootstrapCarrierEnabled(bootstrapCarrierEnabled);
            return this;
        }

        @Override
        public Builder bootstrapHttpDirectPort(final int bootstrapHttpDirectPort) {
            super.bootstrapHttpDirectPort(bootstrapHttpDirectPort);
            return this;
        }

        @Override
        public Builder bootstrapHttpSslPort(final int bootstrapHttpSslPort) {
            super.bootstrapHttpSslPort(bootstrapHttpSslPort);
            return this;
        }

        @Override
        public Builder bootstrapCarrierDirectPort(final int bootstrapCarrierDirectPort) {
            super.bootstrapCarrierDirectPort(bootstrapCarrierDirectPort);
            return this;
        }

        @Override
        public Builder bootstrapCarrierSslPort(final int bootstrapCarrierSslPort) {
            super.bootstrapCarrierSslPort(bootstrapCarrierSslPort);
            return this;
        }

        @Override
        public Builder ioPoolSize(final int ioPoolSize) {
            super.ioPoolSize(ioPoolSize);
            return this;
        }

        @Override
        public Builder computationPoolSize(final int computationPoolSize) {
            super.computationPoolSize(computationPoolSize);
            return this;
        }

        @Override
        public Builder requestBufferSize(final int requestBufferSize) {
            super.requestBufferSize(requestBufferSize);
            return this;
        }

        @Override
        public Builder responseBufferSize(final int responseBufferSize) {
            super.responseBufferSize(responseBufferSize);
            return this;
        }

        @Override
        public Builder kvEndpoints(final int kvEndpoints) {
            super.kvEndpoints(kvEndpoints);
            return this;
        }

        @Override
        public Builder viewEndpoints(final int viewServiceEndpoints) {
            super.viewEndpoints(viewServiceEndpoints);
            return this;
        }

        @Override
        public Builder queryEndpoints(final int queryServiceEndpoints) {
            super.queryEndpoints(queryServiceEndpoints);
            return this;
        }

        @Override
        public Builder ioPool(final EventLoopGroup group) {
            super.ioPool(group);
            return this;
        }

        @Override
        public Builder ioPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.ioPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder scheduler(final Scheduler scheduler) {
            super.scheduler(scheduler);
            return this;
        }

        @Override
        public Builder scheduler(Scheduler scheduler, ShutdownHook shutdownHook) {
            super.scheduler(scheduler, shutdownHook);
            return this;
        }

        @Override
        public Builder observeIntervalDelay(Delay observeIntervalDelay) {
            super.observeIntervalDelay(observeIntervalDelay);
            return this;
        }

        @Override
        public Builder reconnectDelay(Delay reconnectDelay) {
            super.reconnectDelay(reconnectDelay);
            return this;
        }

        @Override
        public Builder retryDelay(Delay retryDelay) {
            super.retryDelay(retryDelay);
            return this;
        }

        @Override
        public Builder retryStrategy(RetryStrategy retryStrategy) {
            super.retryStrategy(retryStrategy);
            return this;
        }

        @Override
        public Builder maxRequestLifetime(long maxRequestLifetime) {
            super.maxRequestLifetime(maxRequestLifetime);
            return this;
        }

        @Override
        public Builder keepAliveInterval(long keepAliveIntervalMilliseconds) {
            super.keepAliveInterval(keepAliveIntervalMilliseconds);
            return this;
        }

        @Override
        public Builder autoreleaseAfter(long autoreleaseAfter) {
            super.autoreleaseAfter(autoreleaseAfter);
            return this;
        }

        @Override
        public Builder eventBus(EventBus eventBus) {
            super.eventBus(eventBus);
            return this;
        }

        @Override
        public Builder bufferPoolingEnabled(boolean bufferPoolingEnabled) {
            super.bufferPoolingEnabled(bufferPoolingEnabled);
            return this;
        }

        @Override
        public Builder packageNameAndVersion(final String packageNameAndVersion) {
            super.packageNameAndVersion(packageNameAndVersion);
            return this;
        }

        @Override
        public Builder userAgent(final String userAgent) {
            super.userAgent(userAgent);
            return this;
        }

