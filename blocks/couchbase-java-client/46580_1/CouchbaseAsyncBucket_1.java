    public <D extends Document<?>> Observable<D> upsert(final D document, final PersistTo persistTo,
        final ReplicateTo replicateTo) {
        Observable<D> upsertResult = upsert(document);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return upsertResult;
        }

        return upsertResult.flatMap(new Func1<D, Observable<D>>() {
