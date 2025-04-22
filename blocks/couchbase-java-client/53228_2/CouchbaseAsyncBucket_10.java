        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        return Observable.defer(new Func0<Observable<RemoveResponse>>() {
            @Override
            public Observable<RemoveResponse> call() {
                return core.send(new RemoveRequest(document.id(), document.cas(), bucket));
            }
        }).map(new Func1<RemoveResponse, D>() {
            @Override
            public D call(final RemoveResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
