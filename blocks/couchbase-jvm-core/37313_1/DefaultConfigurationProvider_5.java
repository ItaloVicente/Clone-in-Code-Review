    @Override
    public Observable<ClusterConfig> closeBucket(String name) {
        return Observable.from(name).map(new Func1<String, ClusterConfig>() {
            @Override
            public ClusterConfig call(String bucket) {
                removeBucketConfig(bucket);
                return currentConfig.get();
            }
        });
    }

    @Override
    public Observable<ClusterConfig> closeBuckets() {
        return Observable
            .from(currentConfig.get().bucketConfigs().keySet())
            .flatMap(new Func1<String, Observable<? extends ClusterConfig>>() {
                @Override
                public Observable<? extends ClusterConfig> call(String bucketName) {
                    return closeBucket(bucketName);
                }
            }).last();
    }

