    public Boolean unlock(String id, long cas, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .unlock(id, cas)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
