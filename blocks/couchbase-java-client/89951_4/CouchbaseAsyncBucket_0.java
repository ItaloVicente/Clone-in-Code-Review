    public Observable<JsonDocument> get(String id, long timeout, TimeUnit timeUnit) {
        return get(id, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> get(D document, long timeout, TimeUnit timeUnit) {
        return (Observable<D>) Get.get(document.id(), document.getClass(), environment, bucket, core, transcoders, timeout, timeUnit);
    }
