    public LongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .counter(id, delta, initial)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
