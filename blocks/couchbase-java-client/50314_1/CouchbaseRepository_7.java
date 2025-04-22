    @Override
    public <T> List<T> getFromReplica(String id, ReplicaMode type, Class<T> documentClass) {
        return getFromReplica(id, type, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> List<T> getFromReplica(String id, ReplicaMode type, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.getFromReplica(id, type, documentClass).toList(), timeout, timeUnit);
    }

    @Override
    public <T> T getAndLock(String id, int lockTime, Class<T> documentClass) {
        return getAndLock(id, lockTime, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T getAndLock(String id, int lockTime, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.getAndLock(id, lockTime, documentClass), timeout, timeUnit);
    }

    @Override
    public <T> T getAndTouch(String id, int expiry, Class<T> documentClass) {
        return getAndTouch(id, expiry, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T getAndTouch(String id, int expiry, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.getAndTouch(id, expiry, documentClass), timeout, timeUnit);
    }

    @Override
    public boolean exists(String id) {
        return exists(id, timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean exists(String id, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.exists(id), timeout, timeUnit);
    }

    @Override
    public <T> boolean exists(T document) {
        return exists(document, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> boolean exists(T document, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.exists(document), timeout, timeUnit);
    }

    @Override
    public <T> T upsert(T document, PersistTo persistTo) {
        return upsert(document, persistTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T upsert(T document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T upsert(T document, ReplicateTo replicateTo) {
        return upsert(document, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T upsert(T document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <T> T upsert(T document, PersistTo persistTo, ReplicateTo replicateTo) {
        return upsert(document, persistTo, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T upsert(T document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.upsert(document, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> T insert(T document, PersistTo persistTo) {
        return upsert(document, persistTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T insert(T document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T insert(T document, ReplicateTo replicateTo) {
        return upsert(document, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T insert(T document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <T> T insert(T document, PersistTo persistTo, ReplicateTo replicateTo) {
        return upsert(document, persistTo, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T insert(T document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.upsert(document, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> T insert(T document) {
        return insert(document, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T insert(T document, long timeout, TimeUnit timeUnit) {
        return insert(document, PersistTo.NONE, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T replace(T document) {
        return replace(document, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T replace(T document, long timeout, TimeUnit timeUnit) {
        return replace(document, PersistTo.NONE, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T replace(T document, PersistTo persistTo) {
        return upsert(document, persistTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T replace(T document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T replace(T document, ReplicateTo replicateTo) {
        return upsert(document, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T replace(T document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return upsert(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <T> T replace(T document, PersistTo persistTo, ReplicateTo replicateTo) {
        return upsert(document, persistTo, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T replace(T document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.upsert(document, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> T remove(T document) {
        return remove(document, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(T document, long timeout, TimeUnit timeUnit) {
        return remove(document, PersistTo.NONE, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T remove(T document, PersistTo persistTo) {
        return remove(document, persistTo, ReplicateTo.NONE, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(T document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return remove(document, persistTo, ReplicateTo.NONE, timeout, timeUnit);
    }

    @Override
    public <T> T remove(T document, ReplicateTo replicateTo) {
        return remove(document, PersistTo.NONE, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(T document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return remove(document, PersistTo.NONE, replicateTo, timeout, timeUnit);
    }

    @Override
    public <T> T remove(T document, PersistTo persistTo, ReplicateTo replicateTo) {
        return remove(document, persistTo, replicateTo, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(T document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.remove(document, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> T remove(String id, Class<T> documentClass) {
        return remove(id, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(String id, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return remove(id, PersistTo.NONE, ReplicateTo.NONE, documentClass, timeout, timeUnit);
    }

    @Override
    public <T> T remove(String id, PersistTo persistTo, Class<T> documentClass) {
        return remove(id, persistTo, ReplicateTo.NONE, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(String id, PersistTo persistTo, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return remove(id, persistTo, ReplicateTo.NONE, documentClass, timeout, timeUnit);
    }

    @Override
    public <T> T remove(String id, ReplicateTo replicateTo, Class<T> documentClass) {
        return remove(id, PersistTo.NONE, replicateTo, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(String id, ReplicateTo replicateTo, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return remove(id, PersistTo.NONE, replicateTo, documentClass, timeout, timeUnit);
    }

    @Override
    public <T> T remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<T> documentClass) {
        return remove(id, persistTo, replicateTo, documentClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<T> documentClass, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.remove(id, persistTo, replicateTo, documentClass), timeout, timeUnit);
    }
