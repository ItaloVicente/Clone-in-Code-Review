            tracer = ThresholdLogTracer.create();
            tracerShutdownHook = new ShutdownHook() {
                private volatile boolean shutdown = false;
                @Override
                public Observable<Boolean> shutdown() {
                    return Observable.fromCallable(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            ((ThresholdLogTracer) tracer).shutdown();
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
