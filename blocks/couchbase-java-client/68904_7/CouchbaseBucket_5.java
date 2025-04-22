    @Override
    public <V>V mapGet(String docId, String key, Class<V> valueType){
        return Blocking.blockForSingle(asyncBucket.mapGet(docId, key, valueType), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <V>boolean mapAdd(String docId, String key, V value){
        return Blocking.blockForSingle(asyncBucket.mapAdd(docId, key, value), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <V>boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.mapAdd(docId, key, value, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean mapRemove(String docId, String key){
        return Blocking.blockForSingle(asyncBucket.mapRemove(docId, key), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.mapRemove(docId, key), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int mapSize(String docId){
        return Blocking.blockForSingle(asyncBucket.mapSize(docId), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>E listGet(String docId, int index, Class<E> elementType){
        return Blocking.blockForSingle(asyncBucket.listGet(docId, index, elementType), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean listPush(String docId, E element){
        return Blocking.blockForSingle(asyncBucket.listPush(docId, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.listPush(docId, element, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean listShift(String docId, E element){
        return Blocking.blockForSingle(asyncBucket.listShift(docId, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.listShift(docId, element, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean listRemove(String docId, int index){
        return Blocking.blockForSingle(asyncBucket.listRemove(docId, index), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.listRemove(docId, index, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean listSet(String docId, int index, E element){
        return Blocking.blockForSingle(asyncBucket.listSet(docId, index, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.listSet(docId, index, element, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int listSize(String docId){
        return Blocking.blockForSingle(asyncBucket.listSize(docId), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean setAdd(String docId, E element){
        return Blocking.blockForSingle(asyncBucket.setAdd(docId, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.setAdd(docId, element, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean setExists(String docId, E element){
        return Blocking.blockForSingle(asyncBucket.setExists(docId, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>E setRemove(String docId, E element){
        return Blocking.blockForSingle(asyncBucket.setRemove(docId, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.setRemove(docId, element, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int setSize(String docId){
        return Blocking.blockForSingle(asyncBucket.setSize(docId), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean queueAdd(String docId, E element){
        return Blocking.blockForSingle(asyncBucket.queueAdd(docId, element), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>boolean queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.queueAdd(docId, element, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>E queueRemove(String docId, Class<E> elementType){
        return Blocking.blockForSingle(asyncBucket.queueRemove(docId, elementType), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <E>E queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder){
        return Blocking.blockForSingle(asyncBucket.queueRemove(docId, elementType, mutationOptionBuilder), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public int queueSize(String docId){
        return Blocking.blockForSingle(asyncBucket.queueSize(docId), kvTimeout, TIMEOUT_UNIT);
    }

