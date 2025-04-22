        return registerBucket(name, name, password);
    }

        @Override
    public Observable<Boolean> registerBucket(final String name, final String username, final String password) {
        Observable<BucketStreamingResponse> response = super.registerBucket(name, username, password).flatMap(new Func1<Boolean, Observable<BucketStreamingResponse>>() {
