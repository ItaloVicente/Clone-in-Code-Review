        Observable<BucketStreamingResponse> response = super.registerBucket(name, username, password).flatMap(new Func1<Boolean, Observable<BucketStreamingResponse>>() {
            @Override
            public Observable<BucketStreamingResponse> call(Boolean aBoolean) {
                return cluster()
                    .<BucketStreamingResponse>send(new BucketStreamingRequest(TERSE_PATH, name, username, password))
                    .doOnNext(new Action1<BucketStreamingResponse>() {
                        @Override
                        public void call(BucketStreamingResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new ConfigurationException("Could not load terse config.");
                            }
                        }
                    });
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<BucketStreamingResponse>>() {
            @Override
            public Observable<BucketStreamingResponse> call(Throwable throwable) {
                return cluster()
                    .<BucketStreamingResponse>send(new BucketStreamingRequest(VERBOSE_PATH, name, username, password))
                    .doOnNext(new Action1<BucketStreamingResponse>() {
                        @Override
                        public void call(BucketStreamingResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new ConfigurationException("Could not load terse config.");
