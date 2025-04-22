        return deferAndWatch(new Func1<Subscriber, Observable<? extends BucketSettings>>() {
            @Override
            public Observable<? extends BucketSettings> call(final Subscriber subscriber) {
                return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<BucketsConfigResponse>>() {
                        @Override
                        public Observable<BucketsConfigResponse> call(Boolean aBoolean) {
                            BucketsConfigRequest req = new BucketsConfigRequest(username, password);
                            req.subscriber(subscriber);
                            return core.send(req);
