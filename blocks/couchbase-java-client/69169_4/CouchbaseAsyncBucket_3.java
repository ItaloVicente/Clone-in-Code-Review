    @Override
    public <E> Observable<E> listGet(String docId, int index, Class<E> elementType) {
        return new AsyncCouchbaseList<E>(this, docId).get(index);
    }

    @Override
    public <E> Observable<Boolean> listPush(String docId, E element) {
        return new AsyncCouchbaseList<E>(this, docId).push(element);
    }

    @Override
    public <E> Observable<Boolean> listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return new AsyncCouchbaseList<E>(this, docId).push(element, mutationOptionBuilder);
    }

    @Override
    public <E> Observable<Boolean> listShift(String docId, E element) {
        return new AsyncCouchbaseList<E>(this, docId).shift(element);
    }

    @Override
    public <E> Observable<Boolean> listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return new AsyncCouchbaseList<E>(this, docId).shift(element, mutationOptionBuilder);
    }

    @Override
    public Observable<Boolean> listRemove(String docId, int index) {
        return new AsyncCouchbaseList<Object>(this, docId).remove(index);
    }

    @Override
    public Observable<Boolean> listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder) {
        return new AsyncCouchbaseList<Object>(this, docId).remove(index, mutationOptionBuilder);
    }

    @Override
    public <E> Observable<Boolean> listSet(String docId, int index, E element) {
        return new AsyncCouchbaseList<E>(this, docId).set(index, element);
    }

    @Override
    public <E> Observable<Boolean> listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder) {
        return new AsyncCouchbaseList<E>(this, docId).set(index, element, mutationOptionBuilder);
    }

    @Override
    public Observable<Integer> listSize(String docId) {
        return new AsyncCouchbaseList<Object>(this, docId).size();
    }

