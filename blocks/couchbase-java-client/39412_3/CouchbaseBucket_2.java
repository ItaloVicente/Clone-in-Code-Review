    @Override
    public Observable<JsonDocument> getAndLock(String id, int lockTime) {
        return getAndLock(id, lockTime, JsonDocument.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getAndLock(D document, int lockTime) {
        return (Observable<D>) getAndLock(document.id(), lockTime, document.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getAndLock(final String id, final int lockTime, final Class<D> target) {
        return core.<GetResponse>send(new GetRequest(id, bucket, true, false, lockTime)).map(new Func1<GetResponse, D>() {
            @Override
            public D call(final GetResponse response) {
                Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
                Object content = response.status() == ResponseStatus.SUCCESS ? converter.decode(response.content()) : null;
                return (D) converter.newDocument(id, content, response.cas(), 0, response.status());
            }
        });
    }

    @Override
    public Observable<JsonDocument> getAndTouch(String id, int expiry) {
        return getAndTouch(id, expiry, JsonDocument.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getAndTouch(D document) {
        return (Observable<D>) getAndTouch(document.id(), document.expiry(), document.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getAndTouch(final String id, final int expiry, final Class<D> target) {
        return core.<GetResponse>send(new GetRequest(id, bucket, false, true, expiry)).map(new Func1<GetResponse, D>() {
            @Override
            public D call(final GetResponse response) {
                Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
                Object content = response.status() == ResponseStatus.SUCCESS ? converter.decode(response.content()) : null;
                return (D) converter.newDocument(id, content, response.cas(), 0, response.status());
            }
        });
    }

