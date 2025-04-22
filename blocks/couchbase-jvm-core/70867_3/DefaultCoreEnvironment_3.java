        public Builder kvIoPool(final EventLoopGroup group, final ShutdownHook shutdownHook) {
            this.kvIoPool = group;
            this.kvIoPoolShutdownHook = shutdownHook;
            return this;
        }

        public Builder viewIoPool(final EventLoopGroup group, final ShutdownHook shutdownHook) {
            this.viewIoPool = group;
            this.viewIoPoolShutdownHook = shutdownHook;
            return this;
        }

        public Builder queryIoPool(final EventLoopGroup group, final ShutdownHook shutdownHook) {
            this.queryIoPool = group;
            this.queryIoPoolShutdownHook = shutdownHook;
            return this;
        }

        public Builder searchIoPool(final EventLoopGroup group, final ShutdownHook shutdownHook) {
            this.searchIoPool = group;
            this.searchIoPoolShutdownHook = shutdownHook;
            return this;
        }

