    @Override
    public synchronized Observable<Boolean> shutdown() {
        if (terminated) {
            LOGGER.debug("ConfigurationProvider already shut down, ignoring.");
            return Observable.just(true);
        } else {
            LOGGER.debug("Shutting down ConfigurationProvider.");
            terminated = true;

            return Observable
                .just(true)
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean ignored) {
                        if (configObservable != null) {
                            LOGGER.trace("Completing ConfigObservable for termination.");
                            configObservable.onCompleted();
                        }
                    }
                })
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        Observable<Boolean> shutdownObs = Observable.just(true);
                        for (final Refresher refresher : refreshers.values()) {
                            shutdownObs = shutdownObs.flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                @Override
                                public Observable<Boolean> call(Boolean ignored) {
                                    LOGGER.trace("Initiating {} shutdown.",
                                        refresher.getClass().getSimpleName());
                                    return refresher.shutdown();
                                }
                            });
                        }
                        return shutdownObs;
                    }
                });
        }
    }

