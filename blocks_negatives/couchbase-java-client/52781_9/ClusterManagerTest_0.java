        clusterManager.async()
            .getBuckets()
            .flatMap(new Func1<BucketSettings, Observable<?>>() {
                @Override
                public Observable<?> call(BucketSettings bucketSettings) {
                    return clusterManager.async().removeBucket(bucketSettings.name());
                }
            }).toBlocking().lastOrDefault(null);
