    <T> Observable<T> upsert(T document, PersistTo persistTo);
    <T> Observable<T> upsert(T document, ReplicateTo replicateTo);
    <T> Observable<T> upsert(T document, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<T> insert(T document);
    <T> Observable<T> insert(T document, PersistTo persistTo);
    <T> Observable<T> insert(T document, ReplicateTo replicateTo);
    <T> Observable<T> insert(T document, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<T> replace(T document);
    <T> Observable<T> replace(T document, PersistTo persistTo);
    <T> Observable<T> replace(T document, ReplicateTo replicateTo);
    <T> Observable<T> replace(T document, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<T> remove(T document);
    <T> Observable<T> remove(T document, PersistTo persistTo);
    <T> Observable<T> remove(T document, ReplicateTo replicateTo);
    <T> Observable<T> remove(T document, PersistTo persistTo, ReplicateTo replicateTo);
    <T> Observable<T> remove(String id, Class<T> documentClass);
    <T> Observable<T> remove(String id, PersistTo persistTo, Class<T> documentClass);
    <T> Observable<T> remove(String id, ReplicateTo replicateTo, Class<T> documentClass);
    <T> Observable<T> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<T> documentClass);
