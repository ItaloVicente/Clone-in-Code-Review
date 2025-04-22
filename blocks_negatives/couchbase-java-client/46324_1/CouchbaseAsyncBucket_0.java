    return core.<ReplaceResponse>send(
            new ReplaceRequest(document.id(), encoded.value1(), document.cas(), document.expiry(), encoded.value2(),
                    bucket))
        .flatMap(new Func1<ReplaceResponse, Observable<D>>() {
            @Override
            public Observable<D> call(ReplaceResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
