        Observable<D> insertResult = insert(document);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return insertResult;
        }

        return insertResult.flatMap(new Func1<D, Observable<D>>() {
