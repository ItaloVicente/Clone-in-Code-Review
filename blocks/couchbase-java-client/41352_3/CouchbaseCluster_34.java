    public Bucket openBucket(long timeout, TimeUnit timeUnit) {
        return openBucket(DEFAULT_BUCKET, timeout, timeUnit);
    }

    @Override
    public Bucket openBucket(String name) {
