    public boolean sslEnabled() {
        return sslEnabled;
    }

    @Override
    public String sslKeystoreFile() {
        return sslKeystoreFile;
    }

    @Override
    public String sslKeystorePassword() {
        return sslKeystorePassword;
    }

    @Override
    public boolean queryEnabled() {
        return queryEnabled;
    }

    @Override
    public int queryPort() {
        return queryPort;
    }

    @Override
    public boolean bootstrapHttpEnabled() {
        return bootstrapHttpEnabled;
    }

    @Override
    public boolean bootstrapCarrierEnabled() {
        return bootstrapCarrierEnabled;
    }

    @Override
    public int bootstrapHttpDirectPort() {
        return bootstrapHttpDirectPort;
    }

    @Override
    public int bootstrapHttpSslPort() {
        return bootstrapHttpSslPort;
    }

    @Override
    public int bootstrapCarrierDirectPort() {
        return bootstrapCarrierDirectPort;
    }

    @Override
    public int bootstrapCarrierSslPort() {
        return bootstrapCarrierSslPort;
    }

    @Override
    public int ioPoolSize() {
        return ioPoolSize;
    }

    @Override
    public int computationPoolSize() {
        return computationPoolSize;
    }

    @Override
    public int requestBufferSize() {
        return requestBufferSize;
    }

    @Override
    public int responseBufferSize() {
        return responseBufferSize;
    }

    @Override
    public int binaryServiceEndpoints() {
        return binaryServiceEndpoints;
    }

    @Override
    public int viewServiceEndpoints() {
        return viewServiceEndpoints;
    }

    @Override
    public int queryServiceEndpoints() {
        return queryServiceEndpoints;
    }

    public static class Builder implements CoreEnvironment {

        private boolean sslEnabled = SSL_ENABLED;
        private String sslKeystoreFile = SSL_KEYSTORE_FILE;
        private String sslKeystorePassword = SSL_KEYSTORE_PASSWORD;
        private boolean queryEnabled = QUERY_ENABLED;
        private int queryPort = QUERY_PORT;
        private boolean bootstrapHttpEnabled = BOOTSTRAP_HTTP_ENABLED;
        private boolean bootstrapCarrierEnabled = BOOTSTRAP_CARRIER_ENABLED;
        private int bootstrapHttpDirectPort = BOOTSTRAP_HTTP_DIRECT_PORT;
        private int bootstrapHttpSslPort = BOOTSTRAP_HTTP_SSL_PORT;
        private int bootstrapCarrierDirectPort = BOOTSTRAP_CARRIER_DIRECT_PORT;
        private int bootstrapCarrierSslPort = BOOTSTRAP_CARRIER_SSL_PORT;
        private int ioPoolSize = IO_POOL_SIZE;
        private int computationPoolSize = COMPUTATION_POOL_SIZE;
        private int responseBufferSize = RESPONSE_BUFFER_SIZE;
        private int requestBufferSize = REQUEST_BUFFER_SIZE;
        private int binaryServiceEndpoints = BINARY_ENDPOINTS;
        private int viewServiceEndpoints = VIEW_ENDPOINTS;
        private int queryServiceEndpoints = QUERY_ENDPOINTS;
        private EventLoopGroup ioPool;
        private Scheduler scheduler;

        protected Builder() {

        }


        @Override
        public boolean sslEnabled() {
            return sslEnabled;
        }

        public Builder sslEnabled(final boolean sslEnabled) {
            this.sslEnabled = sslEnabled;
            return this;
        }

        @Override
        public String sslKeystoreFile() {
            return sslKeystoreFile;
        }

        public Builder sslKeystoreFile(final String sslKeystoreFile) {
            this.sslKeystoreFile = sslKeystoreFile;
            return this;
        }

        @Override
        public String sslKeystorePassword() {
            return sslKeystorePassword;
        }

        public Builder sslKeystorePassword(final String sslKeystorePassword) {
            this.sslKeystorePassword = sslKeystorePassword;
            return this;
        }

        @Override
        public boolean queryEnabled() {
            return queryEnabled;
        }

        public Builder queryEnabled(final boolean queryEnabled) {
            this.queryEnabled = queryEnabled;
            return this;
        }

        @Override
        public int queryPort() {
            return queryPort;
        }

        public Builder queryPort(final int queryPort) {
            this.queryPort = queryPort;
            return this;
        }

        @Override
        public boolean bootstrapHttpEnabled() {
            return bootstrapHttpEnabled;
        }

        public Builder bootstrapHttpEnabled(final boolean bootstrapHttpEnabled) {
            this.bootstrapHttpEnabled = bootstrapHttpEnabled;
            return this;
        }

        @Override
        public boolean bootstrapCarrierEnabled() {
            return bootstrapCarrierEnabled;
        }

        public Builder bootstrapCarrierEnabled(final boolean bootstrapCarrierEnabled) {
            this.bootstrapCarrierEnabled = bootstrapCarrierEnabled;
            return this;
        }

        @Override
        public int bootstrapHttpDirectPort() {
            return bootstrapHttpDirectPort;
        }

        public Builder bootstrapHttpDirectPort(final int bootstrapHttpDirectPort) {
            this.bootstrapHttpDirectPort = bootstrapHttpDirectPort;
            return this;
        }

        @Override
        public int bootstrapHttpSslPort() {
            return bootstrapHttpSslPort;
        }

        public Builder bootstrapHttpSslPort(final int bootstrapHttpSslPort) {
            this.bootstrapHttpSslPort = bootstrapHttpSslPort;
            return this;
        }

        @Override
        public int bootstrapCarrierDirectPort() {
            return bootstrapCarrierDirectPort;
        }

        public Builder bootstrapCarrierDirectPort(final int bootstrapCarrierDirectPort) {
            this.bootstrapCarrierDirectPort = bootstrapCarrierDirectPort;
            return this;
        }

        @Override
        public int bootstrapCarrierSslPort() {
            return bootstrapCarrierSslPort;
        }

        public Builder bootstrapCarrierSslPort(final int bootstrapCarrierSslPort) {
            this.bootstrapCarrierSslPort = bootstrapCarrierSslPort;
            return this;
        }

        @Override
        public int ioPoolSize() {
            return ioPoolSize;
        }

        public Builder ioPoolSize(final int ioPoolSize) {
            this.ioPoolSize = ioPoolSize;
            return this;
        }

        @Override
        public int computationPoolSize() {
            return computationPoolSize;
        }

        public Builder computationPoolSize(final int computationPoolSize) {
            this.computationPoolSize = computationPoolSize;
            return this;
        }

        @Override
        public int requestBufferSize() {
            return requestBufferSize;
        }

        public Builder requestBufferSize(final int requestBufferSize) {
            this.requestBufferSize = requestBufferSize;
            return this;
        }

        @Override
        public int responseBufferSize() {
            return responseBufferSize;
        }

        public Builder responseBufferSize(final int responseBufferSize) {
            this.responseBufferSize = responseBufferSize;
            return this;
        }

        @Override
        public int binaryServiceEndpoints() {
            return binaryServiceEndpoints;
        }

        public Builder binaryServiceEndpoints(final int binaryServiceEndpoints) {
            this.binaryServiceEndpoints = binaryServiceEndpoints;
            return this;
        }

        @Override
        public int viewServiceEndpoints() {
            return viewServiceEndpoints;
        }

        public Builder viewServiceEndpoints(final int viewServiceEndpoints) {
            this.viewServiceEndpoints = viewServiceEndpoints;
            return this;
        }

        @Override
        public int queryServiceEndpoints() {
            return queryServiceEndpoints;
        }

        public Builder queryServiceEndpoints(final int queryServiceEndpoints) {
            this.queryServiceEndpoints = queryServiceEndpoints;
            return this;
        }

        @Override
        public Observable<Boolean> shutdown() {
            throw new UnsupportedOperationException("Shutdown should not be called on the Builder.");
        }

        @Override
        public EventLoopGroup ioPool() {
            return ioPool;
        }

        public Builder ioPool(final EventLoopGroup group) {
            this.ioPool = group;
            return this;
        }

        @Override
        public Scheduler scheduler() {
            return scheduler;
        }

        public Builder scheduler(final Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        public DefaultCoreEnvironment build() {
            return new DefaultCoreEnvironment(this);
        }
