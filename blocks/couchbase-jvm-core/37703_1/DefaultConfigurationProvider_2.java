                public void call(final Tuple2<LoaderType, BucketConfig> tuple) {
                    registerBucketForRefresh(tuple.value1(), tuple.value2());
                }
            })
            .map(new Func1<Tuple2<LoaderType, BucketConfig>, ClusterConfig>() {
                @Override
                public ClusterConfig call(final Tuple2<LoaderType, BucketConfig> tuple) {
                    upsertBucketConfig(tuple.value2());
