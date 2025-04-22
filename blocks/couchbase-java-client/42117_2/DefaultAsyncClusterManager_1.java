            }));
    }

    private Observable<BucketSettings> ensureBucketIsHealthy(final Observable<BucketSettings> input) {
        return input.flatMap(new Func1<BucketSettings, Observable<BucketSettings>>() {
            @Override
            public Observable<BucketSettings> call(final BucketSettings bucketSettings) {
                return info()
                    .filter(new Func1<ClusterInfo, Boolean>() {
                        @Override
                        public Boolean call(ClusterInfo clusterInfo) {
                            boolean allHealthy = true;
                            for (Object n : clusterInfo.raw().getArray("nodes")) {
                                JsonObject node = (JsonObject) n;
                                if (!node.getString("status").equals("healthy")) {
                                    allHealthy = false;
                                    break;
                                }
                            }
                            return allHealthy;
                        }
                    })
                    .repeat()
                    .delay(1, TimeUnit.SECONDS)
                    .take(1)
                    .flatMap(new Func1<ClusterInfo, Observable<BucketSettings>>() {
                        @Override
                        public Observable<BucketSettings> call(ClusterInfo clusterInfo) {
                            return Observable.just(bucketSettings);
                        }
                    });
            }
        });
