        @Override
        public long keepAliveInterval() {
            return keepAliveInterval;
        }

        /**
         * Sets the time of inactivity, in milliseconds, after which some services
         * will issue a form of keep-alive request to their corresponding server/nodes
         * (default is 30s, values <= 0 deactivate the idle check).
         */
        public Builder keepAliveInterval(long keepAliveIntervalMilliseconds) {
            this.keepAliveInterval = keepAliveIntervalMilliseconds;
            return this;
        }

