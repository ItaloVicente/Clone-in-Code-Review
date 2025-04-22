        return deferAndWatch(new Func1<Subscriber, Observable<? extends BucketSettings>>() {
            @Override
            public Observable<? extends BucketSettings> call(final Subscriber subscriber) {
                return ensureBucketIsHealthy(hasBucket(settings.name())
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean exists) {
                            if(!exists) {
                                throw new BucketDoesNotExistException("Bucket " + settings.name() + " does not exist!");
                            }
                        }
                    }).flatMap(new Func1<Boolean, Observable<UpdateBucketResponse>>() {
                        @Override
                        public Observable<UpdateBucketResponse> call(Boolean exists) {
                            UpdateBucketRequest req = new UpdateBucketRequest(settings.name(), payload, username, password);
                            req.subscriber(subscriber);
                            return core.send(req);
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<UpdateBucketResponse, BucketSettings>() {
                        @Override
                        public BucketSettings call(UpdateBucketResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new CouchbaseException("Could not update bucket: " + response.config());
                            }
                            return settings;
                        }
                    }));
            }
        });

