    @Override
    public JsonLongDocument counter(String id, long delta, PersistTo persistTo) {
        return counter(id, delta, persistTo, ReplicateTo.NONE);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, ReplicateTo replicateTo) {
        return counter(id, delta, PersistTo.NONE, replicateTo);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return counter(id, delta, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return counter(id, delta, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo) {
        return counter(id, delta, initial, persistTo, ReplicateTo.NONE);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, ReplicateTo replicateTo) {
        return counter(id, delta, initial, PersistTo.NONE, replicateTo);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, initial, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return counter(id, delta, initial, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return counter(id, delta, initial, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo) {
        return counter(id, delta, initial, expiry, persistTo, ReplicateTo.NONE);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo) {
        return counter(id, delta, initial, expiry, PersistTo.NONE, replicateTo);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, initial, expiry, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return counter(id, delta, initial, expiry, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return counter(id, delta, initial, expiry, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.counter(id, delta, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.counter(id, delta, initial, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, int expiry, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.counter(id, delta, initial, expiry, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> D append(D document, PersistTo persistTo) {
        return append(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> D append(D document, ReplicateTo replicateTo) {
        return append(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> D append(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return append(document, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D append(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return append(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> D append(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return append(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> D append(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.append(document, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, PersistTo persistTo) {
        return prepend(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, ReplicateTo replicateTo) {
        return prepend(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return prepend(document, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return prepend(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return prepend(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.prepend(document, persistTo, replicateTo), timeout, timeUnit);
    }

