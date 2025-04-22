        return deferAndWatch(new Func1<Subscriber, Observable<? extends BucketSettings>>() {
            @Override
            public Observable<? extends BucketSettings> call(final Subscriber subscriber) {
                return ensureBucketIsHealthy(hasBucket(settings.name())
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean exists) {
                            if (exists) {
                                throw new BucketAlreadyExistsException("Bucket " + settings.name() + " already exists!");
                            }
                        }
                    }).flatMap(new Func1<Boolean, Observable<InsertBucketResponse>>() {
                        @Override
                        public Observable<InsertBucketResponse> call(Boolean exists) {
                            InsertBucketRequest req = new InsertBucketRequest(payload, username, password);
                            req.subscriber(subscriber);
                            return core.send(req);
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<InsertBucketResponse, BucketSettings>() {
                        @Override
                        public BucketSettings call(InsertBucketResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new CouchbaseException("Could not insert bucket: " + response.config());
                            }
                            return settings;
                        }
                    }));
            }
        });
