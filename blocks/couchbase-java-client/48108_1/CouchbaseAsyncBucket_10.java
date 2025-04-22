        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<UpsertResponse>>() {
            @Override
            public Observable<UpsertResponse> call() {
                return core.send(new UpsertRequest(document.id(), encoded.value1(),
                    document.expiry(), encoded.value2(), bucket));
            }
        }))
        .map(new Func1<UpsertResponse, D>() {
            @Override
            public D call(UpsertResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
