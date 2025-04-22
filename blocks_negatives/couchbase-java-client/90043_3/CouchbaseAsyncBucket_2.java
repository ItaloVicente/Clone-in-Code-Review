        return deferAndWatch(new Func1<Subscriber, Observable<ReplaceResponse>>() {
            @Override
            public Observable<ReplaceResponse> call(Subscriber s) {
                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                ReplaceRequest request = new ReplaceRequest(document.id(), encoded.value1(), document.cas(), document.expiry(), encoded.value2(), bucket);
                request.subscriber(s);
                return core.send(request);
            }
        }).map(new Func1<ReplaceResponse, D>() {
            @Override
            public D call(ReplaceResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
