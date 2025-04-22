    public <D extends Document<?>> List<D> getFromReplica(String id, ReplicaMode type, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getFromReplica(id, type, target)
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
