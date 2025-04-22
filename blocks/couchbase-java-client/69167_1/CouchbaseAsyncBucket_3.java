    @Override
    public <V> Observable<V> mapGet(String docId, String key, Class<V> valueType) {
        return new AsyncCouchbaseMap<V>(this, docId).get(key);
    }

    @Override
    public <V> Observable<Boolean> mapAdd(String docId, String key, V value) {
        return new AsyncCouchbaseMap<V>(this, docId).add(key, value);
    }

    @Override
    public <V> Observable<Boolean> mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder) {
        return new AsyncCouchbaseMap<V>(this, docId).add(key, value, mutationOptionBuilder);
    }

    @Override
    public Observable<Boolean> mapRemove(String docId, String key) {
        return new AsyncCouchbaseMap<Object>(this, docId).remove(key);
    }

    @Override
    public Observable<Boolean> mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder) {
        return new AsyncCouchbaseMap<Object>(this, docId).remove(key, mutationOptionBuilder);
    }

    @Override
    public Observable<Integer> mapSize(String docId) {
        return new AsyncCouchbaseMap<Object>(this, docId).size();
    }

