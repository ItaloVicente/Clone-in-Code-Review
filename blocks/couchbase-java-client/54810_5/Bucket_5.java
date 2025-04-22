    JsonLongDocument counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial);

    JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo);

    JsonLongDocument counter(String id, long delta, long initial, ReplicateTo replicateTo);

    JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo);

    JsonLongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, int expiry);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D append(D document);

    <D extends Document<?>> D append(D document, PersistTo persistTo);

    <D extends Document<?>> D append(D document, ReplicateTo replicateTo);

    <D extends Document<?>> D append(D document, PersistTo persistTo, ReplicateTo replicateTo);

    <D extends Document<?>> D append(D document, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D append(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit);

