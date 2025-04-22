    Observable<Boolean> exists(String id);
    <T> Observable<Boolean> exists(EntityDocument<T> document);

    <T> Observable<EntityDocument<T>> upsert(EntityDocument<T> document);
    <T> Observable<EntityDocument<T>> upsert(EntityDocument<T> document, PersistTo persistTo);
    <T> Observable<EntityDocument<T>> upsert(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> Observable<EntityDocument<T>> upsert(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<EntityDocument<T>> insert(EntityDocument<T> document);
    <T> Observable<EntityDocument<T>> insert(EntityDocument<T> document, PersistTo persistTo);
    <T> Observable<EntityDocument<T>> insert(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> Observable<EntityDocument<T>> insert(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<EntityDocument<T>> replace(EntityDocument<T> document);
    <T> Observable<EntityDocument<T>> replace(EntityDocument<T> document, PersistTo persistTo);
    <T> Observable<EntityDocument<T>> replace(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> Observable<EntityDocument<T>> replace(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document);
    <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document, PersistTo persistTo);
    <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);
    <T> Observable<EntityDocument<T>> remove(String id, Class<T> documentClass);
    <T> Observable<EntityDocument<T>> remove(String id, PersistTo persistTo, Class<T> documentClass);
    <T> Observable<EntityDocument<T>> remove(String id, ReplicateTo replicateTo, Class<T> documentClass);
    <T> Observable<EntityDocument<T>> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<T> documentClass);
