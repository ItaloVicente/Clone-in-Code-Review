                        })
                        .repeat()
                        .take(1)
                        .flatMap(new Func1<ClusterInfo, Observable<BucketSettings>>() {
                            @Override
                            public Observable<BucketSettings> call(ClusterInfo clusterInfo) {
                                return Observable.just(bucketSettings);
                            }
                        });
