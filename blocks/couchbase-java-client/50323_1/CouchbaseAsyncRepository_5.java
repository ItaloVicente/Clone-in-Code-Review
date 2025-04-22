    public <T> Observable<EntityDocument<T>> insert(final EntityDocument<T> document) {
        return insert(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> insert(EntityDocument<T> document, PersistTo persistTo) {
        return insert(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> insert(EntityDocument<T> document, ReplicateTo replicateTo) {
        return insert(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<EntityDocument<T>> insert(final EntityDocument<T> document, final PersistTo persistTo, final ReplicateTo replicateTo) {
