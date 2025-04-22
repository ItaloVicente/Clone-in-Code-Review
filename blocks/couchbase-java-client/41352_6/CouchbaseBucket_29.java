    public Boolean touch(String id, int expiry, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .touch(id, expiry)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
