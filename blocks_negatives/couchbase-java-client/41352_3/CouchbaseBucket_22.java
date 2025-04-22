    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> append(final D document) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
        return core
            .<AppendResponse>send(new AppendRequest(document.id(), document.cas(), encoded.value1(), bucket))
            .map(new Func1<AppendResponse, D>() {
                @Override
                public D call(final AppendResponse response) {
                    if (response.status() == ResponseStatus.FAILURE) {
                        throw new DocumentDoesNotExistException();
                    }
