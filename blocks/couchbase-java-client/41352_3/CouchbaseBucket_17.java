    public <D extends Document<?>> D getAndTouch(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getAndTouch(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
