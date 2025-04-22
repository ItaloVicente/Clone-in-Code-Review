        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<AppendResponse>>() {
            @Override
            public Observable<AppendResponse> call() {
                return core.send(new AppendRequest(document.id(), document.cas(), encoded.value1(), bucket));
            }
        }))
        .map(new Func1<AppendResponse, D>() {
            @Override
            public D call(final AppendResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
