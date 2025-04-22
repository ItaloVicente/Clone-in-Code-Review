
    @Override
    public <D extends Document<?>> Observable<D> insert(D document, PersistTo persistTo) {
        return insert(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> Observable<D> insert(D document, ReplicateTo replicateTo) {
        return insert(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(D document, PersistTo persistTo) {
        return upsert(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(D document, ReplicateTo replicateTo) {
        return upsert(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> replace(D document, PersistTo persistTo) {
        return replace(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> Observable<D> replace(D document, ReplicateTo replicateTo) {
        return replace(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo) {
        return remove(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(D document, ReplicateTo replicateTo) {
        return remove(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public Observable<JsonDocument> remove(String id, PersistTo persistTo) {
        return remove(id, persistTo, ReplicateTo.NONE);
    }

    @Override
    public Observable<JsonDocument> remove(String id, ReplicateTo replicateTo) {
        return remove(id, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, Class<D> target) {
        return remove(id, persistTo, ReplicateTo.NONE, target);
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, ReplicateTo replicateTo, Class<D> target) {
        return remove(id, PersistTo.NONE, replicateTo, target);
    }
