        @Override
        public int keepAlive() {
            return keepAlive;
        }

        public Builder keepAlive(int keepAliveSeconds) {
            this.keepAlive = keepAliveSeconds;
            return this;
        }

