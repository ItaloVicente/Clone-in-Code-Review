    public Observable<Boolean> unlock(final String id, final long cas) {
        return Observable.defer(new Func0<Observable<UnlockResponse>>() {
            @Override
            public Observable<UnlockResponse> call() {
                return core.send(new UnlockRequest(id, cas, bucket));
            }
        }).map(new Func1<UnlockResponse, Boolean>() {
            @Override
            public Boolean call(UnlockResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
