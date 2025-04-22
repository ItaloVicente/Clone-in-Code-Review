        Observable.from(BUCKETS)
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String bucket) {
                        return clusterManager.async().removeBucket(bucket);
                    }
                }).toBlocking().lastOrDefault(null);

        this.bucketsRemaining = clusterManager.async().getBuckets().count().toBlocking().single();
