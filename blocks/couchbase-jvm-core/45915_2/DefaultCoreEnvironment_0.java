        @Override
        public RetryStrategy retryStrategy() {
            return retryStrategy;
        }

        public Builder retryStrategy(final RetryStrategy retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

