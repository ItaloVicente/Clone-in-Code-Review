    public Observable<Boolean> touch(final String id, final int expiry) {

        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<TouchResponse>>() {
            @Override
            public Observable<TouchResponse> call() {
                return core.send(new TouchRequest(id, expiry, bucket));
            }
        }))
        .map(new Func1<TouchResponse, Boolean>() {
