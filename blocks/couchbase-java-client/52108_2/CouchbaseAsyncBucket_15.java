    public Observable<Boolean> touch(final String id, final int expiry) {
        return core.<TouchResponse>send(new RequestFactory() {
            @Override
            public CouchbaseRequest call() {
                return new TouchRequest(id, expiry, bucket);
            }
        }).map(new Func1<TouchResponse, Boolean>() {
