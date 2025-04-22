        BucketConfig config = BucketConfigParser.parse(rawConfig);
        config.password(currentConfig.get().bucketConfig(bucket).password());
        upsertBucketConfig(config);
    }

    private void registerBucketForRefresh(final LoaderType loaderType, final BucketConfig bucketConfig) {
        Refresher refresher = refreshers.get(loaderType);
        if (refresher == null) {
            throw new IllegalStateException("Could not find refresher for loader type: " + loaderType);
