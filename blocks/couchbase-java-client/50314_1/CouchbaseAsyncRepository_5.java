        return upsert(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> upsert(T document, PersistTo persistTo) {
        return upsert(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> upsert(T document, ReplicateTo replicateTo) {
        return upsert(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<T> upsert(final T document, final PersistTo persistTo, final ReplicateTo replicateTo) {
