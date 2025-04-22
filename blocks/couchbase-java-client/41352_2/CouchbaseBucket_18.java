    public <D extends Document<?>> D insert(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .insert(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
