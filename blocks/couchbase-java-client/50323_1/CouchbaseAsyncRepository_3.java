            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<EntityDocument<T>> getFromReplica(String id, final ReplicaMode type, Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.getFromReplica(id, type);
                }
            })
            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<EntityDocument<T>> getAndLock(String id, final int lockTime, Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.getAndLock(id, lockTime);
                }
            })
            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<EntityDocument<T>> getAndTouch(String id, final int expiry, Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.getAndTouch(id, expiry);
                }
            })
            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<EntityDocument<T>> upsert(final EntityDocument<T> document) {
        return upsert(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> upsert(EntityDocument<T> document, PersistTo persistTo) {
        return upsert(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> upsert(EntityDocument<T> document, ReplicateTo replicateTo) {
        return upsert(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<EntityDocument<T>> upsert(final EntityDocument<T> document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return Observable
            .just(document)
            .flatMap(new Func1<EntityDocument<T>, Observable<? extends Document<?>>>() {
