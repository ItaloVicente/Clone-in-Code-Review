    @Override
    public Iterator<JsonDocument> getFromReplica(String id) {
        return getFromReplica(id, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> Iterator<D> getFromReplica(D document) {
        return getFromReplica(document, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> Iterator<D> getFromReplica(String id, Class<D> target) {
        return getFromReplica(id, target, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> Iterator<D> getFromReplica(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getFromReplica(id, ReplicaMode.ALL, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .getIterator();
    }

    @Override
    public <D extends Document<?>> Iterator<D> getFromReplica(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getFromReplica(document, ReplicaMode.ALL)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .getIterator();
    }

    @Override
    public Iterator<JsonDocument> getFromReplica(String id, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getFromReplica(id, ReplicaMode.ALL)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .getIterator();
    }

