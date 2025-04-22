        if (!zombieResponseReportingEnabled) {
            zombieResponseReporter = null;
            zombieReporterShutdownHook = new NoOpShutdownHook();
        } else if (builder.zombieResponseReporter == null){
            zombieResponseReporter = DefaultZombieResponseReporter.create();
            zombieReporterShutdownHook = new ShutdownHook() {
                private volatile boolean shutdown = false;

                @Override
                public Observable<Boolean> shutdown() {
                    return Observable.fromCallable(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            zombieResponseReporter.shutdown();
                            shutdown = true;
                            return true;
                        }
                    });
                }

                @Override
                public boolean isShutdown() {
                    return shutdown;
                }
            };
        } else {
            zombieResponseReporter = builder.zombieResponseReporter;
            zombieReporterShutdownHook = new NoOpShutdownHook();
        }

