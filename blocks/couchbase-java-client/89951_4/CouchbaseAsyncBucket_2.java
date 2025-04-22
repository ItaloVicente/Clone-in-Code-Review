    @Override
    public Observable<JsonDocument> getAndLock(String id, int lockTime, long timeout, TimeUnit timeUnit) {
        return getAndLock(id, lockTime, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> getAndLock(D document, int lockTime, long timeout, TimeUnit timeUnit) {
        return (Observable<D>) getAndLock(document.id(), lockTime, document.getClass(), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> getAndLock(String id, int lockTime, Class<D> target, long timeout, TimeUnit timeUnit) {
        return Get.getAndLock(id, target, environment, bucket, core, transcoders, lockTime, timeout, timeUnit);
    }

    @Override
    public Observable<JsonDocument> getAndTouch(String id, int expiry, long timeout, TimeUnit timeUnit) {
        return getAndTouch(id, expiry, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> getAndTouch(D document, long timeout, TimeUnit timeUnit) {
        return (Observable<D>) getAndTouch(document.id(), document.expiry(), document.getClass(), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> getAndTouch(String id, int expiry, Class<D> target, long timeout, TimeUnit timeUnit) {
        return Get.getAndTouch(id, target, environment, bucket, core, transcoders, expiry, timeout, timeUnit);
    }

