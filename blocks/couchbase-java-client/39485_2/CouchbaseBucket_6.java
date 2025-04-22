    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return (Observable<D>) remove(document.id(), persistTo, replicateTo, document.getClass());
    }

    @Override
    public Observable<JsonDocument> remove(String id, PersistTo persistTo, ReplicateTo replicateTo) {
        return null;
    }

    @Override
    public <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target) {
        return null;
    }

    @Override
