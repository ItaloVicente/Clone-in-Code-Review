    public <D extends Document<?>> D get(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .get(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
