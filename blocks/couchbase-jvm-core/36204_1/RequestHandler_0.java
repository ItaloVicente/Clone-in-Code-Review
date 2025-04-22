    private void reconfigure() {
        ClusterConfig config = configuration.get();
        for (Map.Entry<String, BucketConfig> bucket : config.bucketConfigs().entrySet()) {
            BucketConfig bucketConfig = bucket.getValue();
            reconfigureBucket(bucketConfig);
        }
    }

    private void reconfigureBucket(final BucketConfig config) {
        System.out.println(config);
    }

