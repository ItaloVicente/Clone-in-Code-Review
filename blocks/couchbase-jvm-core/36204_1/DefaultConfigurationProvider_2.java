            new Func1<BucketConfig, ClusterConfig>() {
                @Override
                public ClusterConfig call(BucketConfig bucketConfig) {
                    applyBucketConfig(bucket, bucketConfig);
                    return currentConfig.get();
                }
            }
        );*/

