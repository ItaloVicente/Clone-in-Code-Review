    public <D extends Document<?>> D insert(D document, PersistTo persistTo) {
        return insert(document, persistTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D insert(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .insert(document, persistTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D insert(D document, ReplicateTo replicateTo) {
        return insert(document, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D insert(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .insert(document, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D upsert(D document) {
        return upsert(document, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D upsert(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .upsert(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D upsert(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return upsert(document, persistTo, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D upsert(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .upsert(document, persistTo, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D upsert(D document, PersistTo persistTo) {
        return upsert(document, persistTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D upsert(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .upsert(document, persistTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D upsert(D document, ReplicateTo replicateTo) {
        return upsert(document, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D upsert(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .upsert(document, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D replace(D document) {
        return replace(document, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D replace(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .replace(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D replace(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return replace(document, persistTo, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }
