    public <D extends Document<?>> List<D> getFromReplica(D document, ReplicaMode type, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getFromReplica(document, type)
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
