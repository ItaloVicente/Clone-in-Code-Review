        return Observable.defer(new Func0<Observable<InsertResponse>>() {
            @Override
            public Observable<InsertResponse> call() {
                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                return core.send(new InsertRequest(document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket));
            }
        }).map(new Func1<InsertResponse, D>() {
            @Override
            public D call(InsertResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
