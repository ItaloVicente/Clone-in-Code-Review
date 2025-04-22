        Observable<Boolean> result = Observable.merge(
                wrapShutdown(ioPoolShutdownHook.shutdown(), "IoPool"),
                wrapBestEffortShutdown(nettyShutdownHook.shutdown(), "Netty"),
                wrapShutdown(coreSchedulerShutdownHook.shutdown(), "Core Scheduler"))
                .reduce(true,
                        new Func2<Boolean, ShutdownStatus, Boolean>() {
                            @Override
                            public Boolean call(Boolean previousStatus, ShutdownStatus currentStatus) {
                                return previousStatus && currentStatus.success;
                            }
                        });
        return result;
    }

    private Observable<ShutdownStatus> wrapBestEffortShutdown(Observable<Boolean> source, final String target) {
        return wrapShutdown(source, target)
                .map(new Func1<ShutdownStatus, ShutdownStatus>() {
                    @Override
                    public ShutdownStatus call(ShutdownStatus original) {
                        if (original.cause == null && !original.success) {
                            LOGGER.info(target + " shutdown is best effort, ignoring failure");
                            return new ShutdownStatus(target, true, null);
                        } else {
                            return original;
                        }
                    }
                });
    }

    private Observable<ShutdownStatus> wrapShutdown(Observable<Boolean> source, final String target) {
        return source.
                reduce(true, new Func2<Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean previousStatus, Boolean currentStatus) {
                        return previousStatus && currentStatus;
                    }
                })
                .map(new Func1<Boolean, ShutdownStatus>() {
                    @Override
                    public ShutdownStatus call(Boolean status) {
                        return new ShutdownStatus(target, status, null);
                    }
                })
                .onErrorReturn(new Func1<Throwable, ShutdownStatus>() {
                    @Override
                    public ShutdownStatus call(Throwable throwable) {
                        return new ShutdownStatus(target, false, throwable);
                    }
                })
                .doOnNext(new Action1<ShutdownStatus>() {
                    @Override
                    public void call(ShutdownStatus shutdownStatus) {
                        LOGGER.info(shutdownStatus.toString());
                    }
                });
