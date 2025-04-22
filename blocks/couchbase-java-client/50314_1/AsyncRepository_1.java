    <T> Observable<T> getFromReplica(String id, ReplicaMode type, Class<T> documentClass);
    <T> Observable<T> getAndLock(String id, int lockTime, Class<T> documentClass);
    <T> Observable<T> getAndTouch(String id, int expiry, Class<T> documentClass);

    Observable<Boolean> exists(String id);
    <T> Observable<Boolean> exists(T document);
