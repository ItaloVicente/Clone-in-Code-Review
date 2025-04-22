        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
    return core.<ReplaceResponse>send(new ReplaceRequest(document.id(), encoded.value1(), document.cas(), document.expiry(), encoded.value2(), bucket))

        .flatMap(new Func1<ReplaceResponse, Observable<D>>() {
            @Override
            public Observable<D> call(ReplaceResponse response) {
                if (response.status() == ResponseStatus.NOT_EXISTS) {
                    return Observable.error(new DocumentDoesNotExistException());
                }
                return Observable.just((D) transcoder.newDocument(document.id(), document.content(), response.cas(),
                    document.expiry(), response.status()));
            }
        });
