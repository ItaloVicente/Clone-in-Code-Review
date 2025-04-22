    public static class Builder extends DefaultCoreEnvironment.Builder implements CouchbaseEnvironment {

        private long managementTimeout = MANAGEMENT_TIMEOUT;
        private long queryTimeout = QUERY_TIMEOUT;
        private long viewTimeout = VIEW_TIMEOUT;
        private long binaryTimeout = BINARY_TIMEOUT;
        private long connectTimeout = CONNECT_TIMEOUT;
        private long disconnectTimeout = DISCONNECT_TIMEOUT;

        @Override
        public long managementTimeout() {
            return managementTimeout;
        }

        public Builder managementTimeout(long managementTimeout) {
            this.managementTimeout = managementTimeout;
            return this;
        }

        @Override
        public long queryTimeout() {
            return queryTimeout;
        }

        public Builder queryTimeout(long queryTimeout) {
            this.queryTimeout = queryTimeout;
            return this;
        }

        @Override
        public long viewTimeout() {
            return viewTimeout;
        }

        public Builder viewTimeout(long viewTimeout) {
            this.viewTimeout = viewTimeout;
            return this;
        }

        @Override
        public long binaryTimeout() {
            return binaryTimeout;
        }

        public Builder binaryTimeout(long binaryTimeout) {
            this.binaryTimeout = binaryTimeout;
            return this;
        }

        @Override
        public long connectTimeout() {
            return connectTimeout;
        }

        public Builder connectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        @Override
        public long disconnectTimeout() {
            return disconnectTimeout;
        }

        public Builder disconnectTimeout(long disconnectTimeout) {
            this.disconnectTimeout = disconnectTimeout;
            return this;
        }
