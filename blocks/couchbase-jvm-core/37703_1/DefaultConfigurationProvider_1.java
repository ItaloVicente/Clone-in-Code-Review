
        Observable.from(refreshers.values()).flatMap(new Func1<Refresher, Observable<BucketConfig>>() {
            @Override
            public Observable<BucketConfig> call(Refresher refresher) {
                return refresher.configs();
            }
        }).subscribe(new Action1<BucketConfig>() {
            @Override
            public void call(BucketConfig bucketConfig) {
                upsertBucketConfig(bucketConfig);
            }
        });
