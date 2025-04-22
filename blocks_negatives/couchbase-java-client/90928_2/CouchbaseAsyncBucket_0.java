    public Observable<Boolean> unlock(final String id, final long cas) {
        return deferAndWatch(new Func1<Subscriber, Observable<UnlockResponse>>() {
            @Override
            public Observable<UnlockResponse> call(Subscriber s) {
                UnlockRequest request = new UnlockRequest(id, cas, bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<UnlockResponse, Boolean>() {
            @Override
            public Boolean call(UnlockResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
