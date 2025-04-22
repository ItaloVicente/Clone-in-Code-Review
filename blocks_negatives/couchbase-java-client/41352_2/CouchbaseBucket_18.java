    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> prepend(final D document) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
        return core
            .<PrependResponse>send(new PrependRequest(document.id(), document.cas(), encoded.value1(), bucket))
            .map(new Func1<PrependResponse, D>() {
