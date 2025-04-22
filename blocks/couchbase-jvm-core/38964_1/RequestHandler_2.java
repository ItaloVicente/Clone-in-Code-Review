    public Observable<ClusterConfig> reconfigure(final ClusterConfig config) {
        return Observable
            .just(config)
            .flatMap(new Func1<ClusterConfig, Observable<BucketConfig>>() {
                @Override
                public Observable<BucketConfig> call(final ClusterConfig clusterConfig) {
                    return Observable.from(clusterConfig.bucketConfigs().values());
                }
            }).flatMap(new Func1<BucketConfig, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(BucketConfig bucketConfig) {
                    return reconfigureBucket(bucketConfig);
                }
            })
            .last()
            .doOnNext(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    Set<InetAddress> configNodes = new HashSet<InetAddress>();
                    for (Map.Entry<String, BucketConfig> bucket : config.bucketConfigs().entrySet()) {
                        for (final NodeInfo node : bucket.getValue().nodes()) {
                            configNodes.add(node.hostname());
                        }
                    }
