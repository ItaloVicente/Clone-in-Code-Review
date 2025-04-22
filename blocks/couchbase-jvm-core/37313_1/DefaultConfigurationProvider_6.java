
    private void removeBucketConfig(final String name) {
        ClusterConfig cluster = currentConfig.get();
        cluster.deleteBucketConfig(name);
        currentConfig.set(cluster);
        configObservable.onNext(currentConfig.get());
    }
