        if (builder.kvIoPool != null) {
            this.kvIoPool = builder.kvIoPool;
            this.kvIoPoolShutdownHook = builder.kvIoPoolShutdownHook == null
                    ? new NoOpShutdownHook()
                    : builder.kvIoPoolShutdownHook;
        } else {
            this.kvIoPool = null;
            this.kvIoPoolShutdownHook = new NoOpShutdownHook();
        }
        if (builder.queryIoPool != null) {
            this.queryIoPool = builder.queryIoPool;
            this.queryIoPoolShutdownHook = builder.queryIoPoolShutdownHook == null
                ? new NoOpShutdownHook()
                : builder.queryIoPoolShutdownHook;
        } else {
            this.queryIoPool = null;
            this.queryIoPoolShutdownHook = new NoOpShutdownHook();
        }
        if (builder.viewIoPool != null) {
            this.viewIoPool = builder.viewIoPool;
            this.viewIoPoolShutdownHook = builder.viewIoPoolShutdownHook == null
                ? new NoOpShutdownHook()
                : builder.viewIoPoolShutdownHook;
        } else {
            this.viewIoPool = null;
            this.viewIoPoolShutdownHook = new NoOpShutdownHook();
        }
        if (builder.searchIoPool != null) {
            this.searchIoPool = builder.searchIoPool;
            this.searchIoPoolShutdownHook = builder.searchIoPoolShutdownHook == null
                ? new NoOpShutdownHook()
                : builder.searchIoPoolShutdownHook;
        } else {
            this.searchIoPool = null;
            this.searchIoPoolShutdownHook = new NoOpShutdownHook();
        }

