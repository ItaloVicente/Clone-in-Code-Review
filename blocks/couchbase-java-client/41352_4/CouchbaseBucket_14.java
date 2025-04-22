    public <D extends Document<?>> D getAndLock(D document, int lockTime, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getAndLock(document, lockTime)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .singleOrDefault(null);
