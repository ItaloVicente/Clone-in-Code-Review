        return getFromReplica(id, type, target, 0, null);
    }

    @Override
    public Observable<JsonDocument> getFromReplica(String id, ReplicaMode type, long timeout, TimeUnit timeUnit) {
        return getFromReplica(id, type, JsonDocument.class, timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> getFromReplica(D document, ReplicaMode type, long timeout, TimeUnit timeUnit) {
        return (Observable<D>) getFromReplica(document.id(), type, document.getClass(), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> getFromReplica(String id, ReplicaMode type, Class<D> target, long timeout, TimeUnit timeUnit) {
        return ReplicaReader.read(core, id, type, bucket, transcoders, target, environment, timeout, timeUnit);
