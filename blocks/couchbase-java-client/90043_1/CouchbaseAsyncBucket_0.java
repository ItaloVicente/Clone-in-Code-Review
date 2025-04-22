    @Override
    public <D extends Document<?>> Observable<D> insert(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return insert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> insert(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return insert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(D document, long timeout, TimeUnit timeUnit) {
        return upsert(document, PersistTo.NONE, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        return Mutate.upsert(document, environment, transcoder, core, bucket, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> replace(D document, long timeout, TimeUnit timeUnit) {
        return replace(document, PersistTo.NONE, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> replace(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        return Mutate.replace(document, environment, transcoder, core, bucket, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> replace(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return replace(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> replace(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return replace(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
