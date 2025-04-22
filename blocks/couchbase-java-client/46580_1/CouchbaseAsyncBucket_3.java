        Observable<D> removeResult = remove(id, target);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return removeResult;
        }

        return removeResult.flatMap(new Func1<D, Observable<D>>() {
