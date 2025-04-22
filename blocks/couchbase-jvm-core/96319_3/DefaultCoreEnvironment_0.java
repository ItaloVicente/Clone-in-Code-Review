        if (builder.analyticsIoPool != null) {
            this.analyticsIoPool = builder.analyticsIoPool;
            this.analyticsIoPoolShutdownHook = builder.analyticsIoPoolShutdownHook == null
                ? new NoOpShutdownHook()
                : builder.analyticsIoPoolShutdownHook;
        } else {
            this.analyticsIoPool = null;
            this.analyticsIoPoolShutdownHook = new NoOpShutdownHook();
        }
