    @Override
    public <V> boolean mapAdd(String docId, String key, V value) {
        return mapAdd(docId, key, value, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <V> boolean mapAdd(String docId, String key, V value, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.mapAdd(docId, key, value), timeout, timeUnit);
    }

    @Override
    public <V> boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder) {
        return mapAdd(docId, key, value, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <V> boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.mapAdd(docId, key, value, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public <V> V mapGet(String docId, String key, Class<V> valueType) {
        return mapGet(docId, key, valueType, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <V> V mapGet(String docId, String key, Class<V> valueType, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.mapGet(docId, key, valueType), timeout, timeUnit);
    }

    @Override
    public boolean mapRemove(String docId, String key) {
        return mapRemove(docId, key, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean mapRemove(String docId, String key, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.mapRemove(docId, key), timeout, timeUnit);
    }

    @Override
    public boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder) {
        return mapRemove(docId, key, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.mapRemove(docId, key, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public int mapSize(String docId) {
        return mapSize(docId, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int mapSize(String docId, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.mapSize(docId), timeout, timeUnit);
    }
