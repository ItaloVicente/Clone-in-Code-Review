    public Bucket openBucket(String name, long timeout, TimeUnit timeUnit) {
        return openBucket(name, null, timeout, timeUnit);
    }

    @Override
    public Bucket openBucket(String name, String password) {
        return openBucket(name, password, null);
