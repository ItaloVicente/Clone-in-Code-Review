        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public Builder continuousKeepAliveEnabled(final boolean continuousKeepAliveEnabled) {
            this.continuousKeepAliveEnabled = continuousKeepAliveEnabled;
            return this;
        }

        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public Builder keepAliveErrorThreshold(final long keepAliveErrorThreshold) {
            this.keepAliveErrorThreshold = keepAliveErrorThreshold;
            return this;
        }

        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public Builder keepAliveTimeout(final long keepAliveTimeout) {
            this.keepAliveTimeout = keepAliveTimeout;
            return this;
        }

