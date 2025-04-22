    public <D extends Document<?>> D insert(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .insert(document, persistTo, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
