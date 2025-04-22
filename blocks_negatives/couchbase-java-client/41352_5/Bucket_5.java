    <D extends Document<?>> Observable<D> replace(D document, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> remove(D document);
    <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo, ReplicateTo replicateTo);
    <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo);
    <D extends Document<?>> Observable<D> remove(D document, ReplicateTo replicateTo);

    Observable<JsonDocument> remove(String id);
    Observable<JsonDocument> remove(String id, PersistTo persistTo, ReplicateTo replicateTo);
    Observable<JsonDocument> remove(String id, PersistTo persistTo);
    Observable<JsonDocument> remove(String id, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> remove(String id, Class<D> target);
    <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target);
    <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, Class<D> target);
    <D extends Document<?>> Observable<D> remove(String id, ReplicateTo replicateTo, Class<D> target);

    Observable<ViewResult> query(ViewQuery query);
    Observable<QueryResult> query(Query query);
    Observable<QueryResult> query(String query);

    Observable<Boolean> unlock(String id, long cas);
    <D extends Document<?>> Observable<Boolean> unlock(D document);

    Observable<Boolean> touch(String id, int expiry);
    <D extends Document<?>> Observable<Boolean> touch(D document);

    Observable<LongDocument> counter(String id, long delta);
    Observable<LongDocument> counter(String id, long delta, long initial);
    Observable<LongDocument> counter(String id, long delta, long initial, int expiry);

    Observable<BucketManager> bucketManager();

    <D extends Document<?>> Observable<D> append(D document);
    <D extends Document<?>> Observable<D> prepend(D document);
