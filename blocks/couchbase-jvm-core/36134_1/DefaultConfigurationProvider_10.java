
    private void applyBucketConfig(final String name, final BucketConfig config) {
        ClusterConfig cluster = currentConfig.get();
        cluster.setBucketConfig(name, config);
        currentConfig.set(cluster);
        configObservable.onNext(currentConfig.get());
    }
