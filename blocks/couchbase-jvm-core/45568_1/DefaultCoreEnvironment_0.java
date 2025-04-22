        @Override
        public Delay reconnectDelay() {
            return reconnectDelay;
        }

        public Builder reconnectDelay(final Delay reconnectDelay) {
            this.reconnectDelay = reconnectDelay;
            return this;
        }

