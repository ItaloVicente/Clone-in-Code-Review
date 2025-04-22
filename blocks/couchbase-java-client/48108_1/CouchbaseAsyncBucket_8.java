        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<InsertResponse>>() {
            @Override
            public Observable<InsertResponse> call() {
                return core.send(new InsertRequest(document.id(), encoded.value1(), document.expiry(),
                    encoded.value2(), bucket));
            }
        }))
        .map(new Func1<InsertResponse, D>() {
            @Override
            public D call(InsertResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
