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

