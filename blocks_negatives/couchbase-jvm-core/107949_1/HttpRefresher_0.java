        Observable<BucketStreamingResponse> response = super.registerBucket(name, username, password).flatMap(new Func1<Boolean, Observable<BucketStreamingResponse>>() {
            @Override
            public Observable<BucketStreamingResponse> call(Boolean aBoolean) {
                return cluster()
                    .<BucketStreamingResponse>send(new BucketStreamingRequest(TERSE_PATH, name, username, password))
                    .doOnNext(new Action1<BucketStreamingResponse>() {
