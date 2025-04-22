        return Observable.mergeDelayError(
                ioPoolShutdownHook.shutdown(),
                coreSchedulerShutdownHook.shutdown()
        ).reduce(true, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean a, Boolean b) {
                return a && b;
            }
        }).doOnNext(new Action1<Boolean>() {
