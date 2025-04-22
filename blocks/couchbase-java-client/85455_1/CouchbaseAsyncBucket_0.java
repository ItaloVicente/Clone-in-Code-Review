        return get(id, null, target);
    }

    @Override
    public Observable<JsonDocument> get(String id, BaseSpan<?> span) {
        return get(id, span, JsonDocument.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> get(final String id, final BaseSpan<?> parent, final Class<D> target) {
