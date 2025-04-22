    public <D extends Document<?>> D getAndLock(String id, int lockTime, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getAndLock(id, lockTime, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .singleOrDefault(null);
