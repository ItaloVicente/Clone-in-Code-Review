    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> remove(final D document) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        RemoveRequest request = new RemoveRequest(document.id(), document.cas(),
            bucket);
        return core.<RemoveResponse>send(request).map(new Func1<RemoveResponse, D>() {
            @Override
            public D call(RemoveResponse response) {
                return (D) transcoder.newDocument(document.id(), document.expiry(), document.content(), document.cas());
            }
        });
