            observable
            .doOnNext(new Action1<Tuple2<LoaderType, BucketConfig>>() {
                @Override
                public void call(final Tuple2<LoaderType, BucketConfig> tuple) {
                    registerBucketForRefresh(tuple.value1(), tuple.value2());
                }
            })
            .map(new Func1<Tuple2<LoaderType, BucketConfig>, ClusterConfig>() {
                @Override
                public ClusterConfig call(final Tuple2<LoaderType, BucketConfig> tuple) {
                    upsertBucketConfig(tuple.value2());
                    return currentConfig;
                }
            })
            .doOnNext(new Action1<ClusterConfig>() {
                @Override
                public void call(ClusterConfig clusterConfig) {
                    LOGGER.info("Opened bucket " + bucket);
                    if (eventBus != null && eventBus.hasSubscribers()) {
                        eventBus.publish(new BucketOpenedEvent(bucket));
                    }
                    bootstrapped = true;
                }
            })
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    LOGGER.debug("Explicitly closing bucket {} after failed open attempt to clean resources.", bucket);
                    removeBucketConfig(bucket);
                }
            })
            .onErrorResumeNext(new Func1<Throwable, Observable<ClusterConfig>>() {
                @Override
                public Observable<ClusterConfig> call(final Throwable throwable) {
                    return Observable.error(new ConfigurationException("Could not open bucket.", throwable));
                }
            });
