        @Override
        public RetryPolicy retryPolicy() {
            return retryPolicy;
        }

        public Builder retryPolicy(final RetryPolicy retryPolicy) {
            this.retryPolicy = retryPolicy;
            return this;
        }

