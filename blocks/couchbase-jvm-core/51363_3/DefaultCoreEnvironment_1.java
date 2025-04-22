        if (builder.ioPool() == null) {
            this.ioPool = new NioEventLoopGroup(ioPoolSize(), new DefaultThreadFactory("cb-io", true));
            this.ioPoolShutdownHook = new IoPoolShutdownHook(this.ioPool);
        } else {
            this.ioPool = builder.ioPool();
            this.ioPoolShutdownHook = builder.ioPoolShutdownHook == null
                    ? new NoOpShutdownHook()
                    : builder.ioPoolShutdownHook;
        }

        if (builder.scheduler == null) {
            CoreScheduler managed = new CoreScheduler(computationPoolSize());
            this.coreScheduler = managed;
            this.coreSchedulerShutdownHook = managed
            ;
        } else {
            this.coreScheduler = builder.scheduler();
            this.coreSchedulerShutdownHook = builder.schedulerShutdownHook == null
                    ? new NoOpShutdownHook()
                    : builder.schedulerShutdownHook;
        }
