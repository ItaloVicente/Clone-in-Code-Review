            })
            .doOnNext(new Action1<ClusterConfig>() {
                @Override
                public void call(ClusterConfig clusterConfig) {
                    LOGGER.info("Opened bucket " + bucket);
                }
            })
            .onErrorResumeNext(new Func1<Throwable, Observable<ClusterConfig>>() {
