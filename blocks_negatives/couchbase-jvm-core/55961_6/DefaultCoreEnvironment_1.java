        return Observable.mergeDelayError(
            ioPoolShutdownHook.shutdown(),
            coreSchedulerShutdownHook.shutdown(),
            Observable.just(runtimeMetricsCollector.shutdown()),
            Observable.just(networkLatencyMetricsCollector.shutdown())
        ).reduce(true, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean a, Boolean b) {
                return a && b;
            }
        });
