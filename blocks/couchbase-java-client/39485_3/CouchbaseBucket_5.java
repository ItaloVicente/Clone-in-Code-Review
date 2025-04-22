    @Override
    public Observable<JsonDocument> remove(final String id) {
        return remove(id, JsonDocument.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> remove(final String id, final Class<D> target) {
        Converter<?, ?> converter = converters.get(target);
        return remove((D) converter.newDocument(id, null, 0, 0, null));
    }
