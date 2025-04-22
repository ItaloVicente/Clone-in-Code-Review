    Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, PersistTo persistTo);

    Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo);

    Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo);

