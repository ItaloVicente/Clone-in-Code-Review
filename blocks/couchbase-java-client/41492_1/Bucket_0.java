    <D extends Document<?>> D remove(D document, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo);

    <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D remove(D document, PersistTo persistTo);

    <D extends Document<?>> D remove(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D remove(D document, ReplicateTo replicateTo);

    <D extends Document<?>> D remove(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    JsonDocument remove(String id);

