        final ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit) {
        return observeRemove(remove(id, target, timeout, timeUnit), persistTo, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(D document) {
        return remove(document, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return remove(document, persistTo, replicateTo, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return remove(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return remove(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public Observable<JsonDocument> remove(String id, long timeout, TimeUnit timeUnit) {
        return remove(id, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public Observable<JsonDocument> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return remove(id, persistTo, replicateTo, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public Observable<JsonDocument> remove(String id, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return remove(id, persistTo, ReplicateTo.NONE, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public Observable<JsonDocument> remove(String id, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return remove(id, PersistTo.NONE, replicateTo, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(target);
        return remove((D) transcoder.newDocument(id, 0, null, 0, null), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target) {
        return remove(id, persistTo, replicateTo, target, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, Class<D> target, long timeout, TimeUnit timeUnit) {
        return remove(id, persistTo, ReplicateTo.NONE, target, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit) {
        return remove(id, PersistTo.NONE, replicateTo, target, timeout, timeUnit);
