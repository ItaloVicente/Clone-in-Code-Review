        @Override
        public Delay retryDelay() {
            return retryDelay;
        }

        public Builder retryDelay(final Delay retryDelay) {
            this.retryDelay = retryDelay;
            return this;
        }

