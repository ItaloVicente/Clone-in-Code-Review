    <D extends Document<?>> Observable<D> getReplica(String id, ReplicaMode type, Class<D> target);


    Observable<JsonDocument> getAndLock(String id, int lockTime);
    <D extends Document<?>> Observable<D> getAndLock(D document, int lockTime);
    <D extends Document<?>> Observable<D> getAndLock(String id, int lockTime, Class<D> target);

    Observable<JsonDocument> getAndTouch(String id, int expiry);
    <D extends Document<?>> Observable<D> getAndTouch(D document);
    <D extends Document<?>> Observable<D> getAndTouch(String id, int expiry, Class<D> target);
