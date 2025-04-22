    public <D extends Document<?>> D getAndTouch(String id, int expiry, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getAndTouch(id, expiry, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
