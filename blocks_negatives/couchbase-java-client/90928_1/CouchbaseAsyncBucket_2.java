    public Observable<Boolean> touch(final String id, final int expiry) {
        return deferAndWatch(new Func1<Subscriber, Observable<TouchResponse>>() {
            @Override
            public Observable<TouchResponse> call(Subscriber s) {
                TouchRequest request = new TouchRequest(id, expiry, bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<TouchResponse, Boolean>() {
            @Override
            public Boolean call(TouchResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
