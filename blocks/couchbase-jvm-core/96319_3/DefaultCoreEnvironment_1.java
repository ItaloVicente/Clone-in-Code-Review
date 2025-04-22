        public SELF analyticsIoPool(final EventLoopGroup group, final ShutdownHook shutdownHook) {
            this.analyticsIoPool = group;
            this.analyticsIoPoolShutdownHook = shutdownHook;
            return self();
        }

