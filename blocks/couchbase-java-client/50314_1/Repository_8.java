    <T> T get(String id, Class<T> documentClass);
    <T> T get(String id, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    <T> List<T> getFromReplica(String id, ReplicaMode type, Class<T> documentClass);
    <T> List<T> getFromReplica(String id, ReplicaMode type, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    <T> T getAndLock(String id, int lockTime, Class<T> documentClass);
    <T> T getAndLock(String id, int lockTime, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    <T> T getAndTouch(String id, int expiry, Class<T> documentClass);
    <T> T getAndTouch(String id, int expiry, Class<T> documentClass, long timeout, TimeUnit timeUnit);

    boolean exists(String id);
    boolean exists(String id, long timeout, TimeUnit timeUnit);
    <T> boolean exists(T document);
    <T> boolean exists(T document, long timeout, TimeUnit timeUnit);
