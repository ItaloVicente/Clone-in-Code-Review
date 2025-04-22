    JsonLongDocument counter(String id, long delta, PersistTo persistTo);

    JsonLongDocument counter(String id, long delta, ReplicateTo replicateTo);

    JsonLongDocument counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo);

    JsonLongDocument counter(String id, long delta, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, PersistTo persistTo, long timeout, TimeUnit timeUnit);

    JsonLongDocument counter(String id, long delta, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

