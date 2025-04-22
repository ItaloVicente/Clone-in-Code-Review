    public List<JsonDocument> getFromReplica(String id, ReplicaMode type, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getFromReplica(id, type)
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
