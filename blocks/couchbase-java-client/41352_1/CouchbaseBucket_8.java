    public <D extends Document<?>> D get(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .get(id, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
