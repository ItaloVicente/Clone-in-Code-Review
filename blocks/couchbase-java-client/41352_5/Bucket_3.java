    <D extends Document<?>> D replace(D document, ReplicateTo replicateTo);
    <D extends Document<?>> D replace(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D remove(D document);
    <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo);
    <D extends Document<?>> D remove(D document, PersistTo persistTo);
    <D extends Document<?>> D remove(D document, ReplicateTo replicateTo);
    <D extends Document<?>> D remove(D document, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D remove(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D remove(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    JsonDocument remove(String id);
    JsonDocument remove(String id, PersistTo persistTo, ReplicateTo replicateTo);
    JsonDocument remove(String id, PersistTo persistTo);
    JsonDocument remove(String id, ReplicateTo replicateTo);
    JsonDocument remove(String id, long timeout, TimeUnit timeUnit);
    JsonDocument remove(String id, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    JsonDocument remove(String id, PersistTo persistTo, long timeout, TimeUnit timeUnit);
    JsonDocument remove(String id, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D remove(String id, Class<D> target);
    <D extends Document<?>> D remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target);
    <D extends Document<?>> D remove(String id, PersistTo persistTo, Class<D> target);
    <D extends Document<?>> D remove(String id, ReplicateTo replicateTo, Class<D> target);
    <D extends Document<?>> D remove(String id, Class<D> target, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D remove(String id, PersistTo persistTo, Class<D> target, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D remove(String id, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit);

    ViewResult query(ViewQuery query);
    QueryResult query(Query query);
    QueryResult query(String query);
    ViewResult query(ViewQuery query, long timeout, TimeUnit timeUnit);
    QueryResult query(Query query, long timeout, TimeUnit timeUnit);
    QueryResult query(String query, long timeout, TimeUnit timeUnit);

    Boolean unlock(String id, long cas);
    <D extends Document<?>> Boolean unlock(D document);
    Boolean unlock(String id, long cas, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> Boolean unlock(D document, long timeout, TimeUnit timeUnit);

    Boolean touch(String id, int expiry);
    <D extends Document<?>> Boolean touch(D document);
    Boolean touch(String id, int expiry, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> Boolean touch(D document, long timeout, TimeUnit timeUnit);

    LongDocument counter(String id, long delta);
    LongDocument counter(String id, long delta, long initial);
    LongDocument counter(String id, long delta, long initial, int expiry);
    LongDocument counter(String id, long delta, long timeout, TimeUnit timeUnit);
    LongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeUnit);
    LongDocument counter(String id, long delta, long initial, int expiry, long timeout, TimeUnit timeUnit);

    BucketManager bucketManager();

    <D extends Document<?>> D append(D document);
    <D extends Document<?>> D prepend(D document);

    <D extends Document<?>> D append(D document, long timeout, TimeUnit timeUnit);
    <D extends Document<?>> D prepend(D document, long timeout, TimeUnit timeUnit);
