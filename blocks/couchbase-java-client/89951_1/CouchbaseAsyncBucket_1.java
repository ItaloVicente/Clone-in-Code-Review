    @Override
    public <D extends Document<?>> Observable<Boolean> exists(D document, long timeout, TimeUnit timeUnit) {
        return exists(document.id(), timeout, timeUnit);
    }

    @Override
    public Observable<Boolean> exists(final String id) {
        return Exists.exists(id, environment, core, bucket, 0, null);
