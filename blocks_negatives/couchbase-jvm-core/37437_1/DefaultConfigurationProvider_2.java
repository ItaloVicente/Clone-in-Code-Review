            }
        );*/

        return bootstrapThroughCarrierPublication(bucket, password).map(new Func1<BucketConfig, ClusterConfig>() {
            @Override
            public ClusterConfig call(final BucketConfig bucketConfig) {
                upsertBucketConfig(bucket, bucketConfig);
                return currentConfig.get();
            }
        });
