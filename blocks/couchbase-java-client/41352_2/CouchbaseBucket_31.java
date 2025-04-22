    public LongDocument counter(String id, long delta, long initial, int expiry, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .counter(id, delta, initial, expiry)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
