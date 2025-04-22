    @Override
    public Observable<JsonLongDocument> counter(String id, long delta) {
        return counter(id, delta, 0, (TimeUnit) null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, PersistTo persistTo) {
        return counter(id, delta, persistTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, ReplicateTo replicateTo) {
        return counter(id, delta, replicateTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, persistTo, replicateTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial) {
        return counter(id, delta, initial, 0, (TimeUnit) null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, PersistTo persistTo) {
        return counter(id, delta, initial, persistTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, ReplicateTo replicateTo) {
        return counter(id, delta, initial, replicateTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, initial, persistTo, replicateTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry) {
        return counter(id, delta, initial, expiry, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, PersistTo persistTo) {
        return counter(id, delta, initial, expiry, persistTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo) {
        return counter(id, delta, initial, expiry, replicateTo, 0, null);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, initial, expiry, persistTo, replicateTo, 0, null);
    }

