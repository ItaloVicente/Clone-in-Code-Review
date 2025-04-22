    public JsonDocument getAndLock(String id, int lockTime, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .getAndLock(id, lockTime)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .singleOrDefault(null);
