    @Override
    public <D extends Document<?>> Observable<D> append(D document) {
        return append(document, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> append(D document, PersistTo persistTo) {
        return append(document, persistTo, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> append(D document, ReplicateTo replicateTo) {
        return append(document, replicateTo, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> append(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return append(document, persistTo, replicateTo, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document) {
        return prepend(document, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document, PersistTo persistTo) {
        return prepend(document, persistTo, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document, ReplicateTo replicateTo) {
        return prepend(document, replicateTo, 0, null);
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return prepend(document, persistTo, replicateTo, 0, null);
    }

