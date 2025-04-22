    @Override
    public <V>Observable<V> mapGet(String docId, String key, Class<V> valueType){
        return new CouchbaseMap<V>(this, docId).get(key);
    }

    @Override
    public <V>Observable<Boolean> mapAdd(String docId, String key, V value){
        return new CouchbaseMap<V>(this, docId).add(key, value);
    }

    @Override
    public <V>Observable<Boolean> mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseMap<V>(this, docId).add(key, value, mutationOptionBuilder);
    }

    @Override
    public Observable<Boolean> mapRemove(String docId, String key){
        return new CouchbaseMap<Object>(this, docId).remove(key);
    }

    @Override
    public Observable<Boolean> mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseMap<Object>(this, docId).remove(key, mutationOptionBuilder);
    }

    @Override
    public Observable<Integer> mapSize(String docId){
        return new CouchbaseMap<Object>(this, docId).size();
    }

    @Override
    public <E>Observable<E> listGet(String docId, int index, Class<E> elementType){
        return new CouchbaseList<E>(this, docId).get(index);
    }

    @Override
    public <E>Observable<Boolean> listPush(String docId, E element){
        return new CouchbaseList<E>(this, docId).push(element);
    }

    @Override
    public <E>Observable<Boolean> listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseList<E>(this, docId).push(element, mutationOptionBuilder);
    }

    @Override
    public <E>Observable<Boolean> listShift(String docId, E element){
        return new CouchbaseList<E>(this, docId).shift(element);
    }

    @Override
    public <E>Observable<Boolean> listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseList<E>(this, docId).shift(element, mutationOptionBuilder);
    }

    @Override
    public Observable<Boolean> listRemove(String docId, int index){
        return new CouchbaseList<Object>(this, docId).remove(index);
    }

    @Override
    public Observable<Boolean> listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseList<Object>(this, docId).remove(index, mutationOptionBuilder);
    }

    @Override
    public <E>Observable<Boolean> listSet(String docId, int index, E element){
        return new CouchbaseList<E>(this, docId).set(index, element);
    }

    @Override
    public <E>Observable<Boolean> listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseList<E>(this, docId).set(index, element, mutationOptionBuilder);
    }

    @Override
    public Observable<Integer> listSize(String docId){
        return new CouchbaseList<Object>(this, docId).size();
    }

    @Override
    public <E>Observable<Boolean> setAdd(String docId, E element){
        return new CouchbaseSet<E>(this, docId).add(element);
    }

    @Override
    public <E>Observable<Boolean> setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseSet<E>(this, docId).add(element, mutationOptionBuilder);
    }

    @Override
    public <E>Observable<Boolean> setExists(String docId, E element){
        return new CouchbaseSet<E>(this, docId).exists(element);
    }

    @Override
    public <E>Observable<E> setRemove(String docId, E element){
        return new CouchbaseSet<E>(this, docId).remove(element);
    }

    @Override
    public <E>Observable<E> setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseSet<E>(this, docId).remove(element, mutationOptionBuilder);
    }

    @Override
    public Observable<Integer> setSize(String docId){
        return new CouchbaseSet<Object>(this, docId).size();
    }

    @Override
    public <E>Observable<Boolean> queueAdd(String docId, E element){
        return new CouchbaseQueue<E>(this, docId).add(element);
    }

    @Override
    public <E>Observable<Boolean> queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseQueue<E>(this, docId).add(element, mutationOptionBuilder);
    }

    @Override
    public <E>Observable<E> queueRemove(String docId, Class<E> elementType){
        return new CouchbaseQueue<E>(this, docId).remove();
    }

    @Override
    public <E>Observable<E> queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder){
        return new CouchbaseQueue<E>(this, docId).remove(mutationOptionBuilder);
    }

    @Override
    public Observable<Integer> queueSize(String docId){
        return new CouchbaseQueue<Object>(this, docId).size();
    }

