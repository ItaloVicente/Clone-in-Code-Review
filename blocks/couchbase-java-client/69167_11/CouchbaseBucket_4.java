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


    @Override
    public <E> E listGet(String docId, int index, Class<E> elementType) {
        return listGet(docId, index, elementType, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> E listGet(String docId, int index, Class<E> elementType, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listGet(docId, index, elementType), timeout, timeUnit);
    }

    @Override
    public <E> boolean listPush(String docId, E element) {
        return listPush(docId, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean listPush(String docId, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listPush(docId, element), timeout, timeUnit);
    }

    @Override
    public <E> boolean listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return listPush(docId, element, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listPush(docId, element, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public <E> boolean listShift(String docId, E element) {
        return listShift(docId, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean listShift(String docId, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listShift(docId, element), timeout, timeUnit);
    }

    @Override
    public <E> boolean listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return listShift(docId, element, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }


    @Override
    public <E> boolean listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listShift(docId, element, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public boolean listRemove(String docId, int index) {
        return listRemove(docId, index, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean listRemove(String docId, int index, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listRemove(docId, index), timeout, timeUnit);
    }

    @Override
    public boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder) {
        return listRemove(docId, index, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listRemove(docId, index, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public <E> boolean listSet(String docId, int index, E element) {
        return listSet(docId, index, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean listSet(String docId, int index, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listSet(docId, index, element), timeout, timeUnit);
    }

    @Override
    public <E> boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder) {
        return listSet(docId, index, element, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listSet(docId, index, element, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public int listSize(String docId) {
        return listSize(docId, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int listSize(String docId, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.listSize(docId), timeout, timeUnit);
    }

    @Override
    public <E> boolean setAdd(String docId, E element) {
        return setAdd(docId, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean setAdd(String docId, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.setAdd(docId, element), timeout, timeUnit);
    }

    @Override
    public <E> boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return setAdd(docId, element, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.setAdd(docId, element, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public <E> boolean setExists(String docId, E element) {
        return setExists(docId, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean setExists(String docId, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.setExists(docId, element), timeout, timeUnit);
    }

    @Override
    public <E> E setRemove(String docId, E element) {
        return setRemove(docId, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> E setRemove(String docId, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.setRemove(docId, element), timeout, timeUnit);
    }

    @Override
    public <E> E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return setRemove(docId, element, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.setRemove(docId, element, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public int setSize(String docId) {
        return setSize(docId, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int setSize(String docId, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.setSize(docId), timeout, timeUnit);
    }

    @Override
    public <E> boolean queueAdd(String docId, E element) {
        return queueAdd(docId, element, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean queueAdd(String docId, E element, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.queueAdd(docId, element), timeout, timeUnit);
    }

    @Override
    public <E> boolean queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return queueAdd(docId, element, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> boolean queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.queueAdd(docId, element, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public <E> E queueRemove(String docId, Class<E> elementType) {
        return queueRemove(docId, elementType, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> E queueRemove(String docId, Class<E> elementType, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.queueRemove(docId, elementType), timeout, timeUnit);
    }

    @Override
    public <E> E queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder) {
        return queueRemove(docId, elementType, mutationOptionBuilder, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E> E queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.queueRemove(docId, elementType, mutationOptionBuilder), timeout, timeUnit);
    }

    @Override
    public int queueSize(String docId) {
        return queueSize(docId, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int queueSize(String docId, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.queueSize(docId), timeout, timeUnit);
    }
