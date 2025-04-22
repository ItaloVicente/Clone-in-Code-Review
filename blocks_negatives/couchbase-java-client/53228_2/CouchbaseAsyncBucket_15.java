    public Observable<Boolean> unlock(String id, final long cas) {
        return core
            .<UnlockResponse>send(new UnlockRequest(id, cas, bucket))
            .map(new Func1<UnlockResponse, Boolean>() {
                @Override
                public Boolean call(UnlockResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
