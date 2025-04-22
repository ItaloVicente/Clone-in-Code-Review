    public <D extends Document<?>> Observable<D> replace(final D document, final PersistTo persistTo,
        final ReplicateTo replicateTo) {
        Observable<D> replaceResult = replace(document);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return replaceResult;
        }

        return replaceResult.flatMap(new Func1<D, Observable<D>>() {
