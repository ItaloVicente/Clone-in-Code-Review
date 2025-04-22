    Observable<JsonLongDocument> counter(String id, long delta, long initial, PersistTo persistTo);


    Observable<JsonLongDocument> counter(String id, long delta, long initial, ReplicateTo replicateTo);


    Observable<JsonLongDocument> counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo);

