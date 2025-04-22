    public <D extends Document<?>> Boolean unlock(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .unlock(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
