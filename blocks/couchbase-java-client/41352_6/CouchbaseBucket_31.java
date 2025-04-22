    public LongDocument counter(String id, long delta, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .counter(id, delta)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
