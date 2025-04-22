    <T> EntityDocument<T> get(String id, Class<T> documentClass);
    <T> EntityDocument<T> get(String id, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    <T> List<EntityDocument<T>> getFromReplica(String id, ReplicaMode type, Class<T> documentClass);
    <T> List<EntityDocument<T>> getFromReplica(String id, ReplicaMode type, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    <T> EntityDocument<T> getAndLock(String id, int lockTime, Class<T> documentClass);
    <T> EntityDocument<T> getAndLock(String id, int lockTime, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    <T> EntityDocument<T> getAndTouch(String id, int expiry, Class<T> documentClass);
    <T> EntityDocument<T> getAndTouch(String id, int expiry, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    boolean exists(String id);
    boolean exists(String id, long timeout, TimeUnit timeUnit);
    <T> boolean exists(EntityDocument<T> document);
    <T> boolean exists(EntityDocument<T> document, long timeout, TimeUnit timeUnit);

    <T> EntityDocument<T> upsert(EntityDocument<T> document);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, PersistTo persistTo);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, PersistTo persistTo, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);
    <T> EntityDocument<T> upsert(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <T> EntityDocument<T> insert(EntityDocument<T> document);
    <T> EntityDocument<T> insert(EntityDocument<T> document, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> insert(EntityDocument<T> document, PersistTo persistTo);
    <T> EntityDocument<T> insert(EntityDocument<T> document, PersistTo persistTo, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> insert(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> EntityDocument<T> insert(EntityDocument<T> document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> insert(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);
    <T> EntityDocument<T> insert(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <T> EntityDocument<T> replace(EntityDocument<T> document);
    <T> EntityDocument<T> replace(EntityDocument<T> document, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> replace(EntityDocument<T> document, PersistTo persistTo);
    <T> EntityDocument<T> replace(EntityDocument<T> document, PersistTo persistTo, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> replace(EntityDocument<T> document, ReplicateTo replicateTo);
    <T> EntityDocument<T> replace(EntityDocument<T> document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> EntityDocument<T> replace(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo);
    <T> EntityDocument<T> replace(EntityDocument<T> document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
