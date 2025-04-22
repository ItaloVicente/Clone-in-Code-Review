    public <D extends Document<?>> Boolean touch(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .touch(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
