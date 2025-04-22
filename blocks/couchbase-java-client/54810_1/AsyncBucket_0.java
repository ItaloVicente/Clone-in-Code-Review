    Observable<JsonLongDocument> counter(String id, long delta, PersistTo persistTo);

    Observable<JsonLongDocument> counter(String id, long delta, ReplicateTo replicateTo);

    Observable<JsonLongDocument> counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo);

