        @Override
        public Builder dcpEnabled(boolean dcpEnabled) {
            if (dcpEnabled) {
                throw new IllegalArgumentException("DCP is not supported from the Java SDK.");
            }

            super.dcpEnabled(dcpEnabled);
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

