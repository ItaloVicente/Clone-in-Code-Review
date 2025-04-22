    public Bucket openBucket(String name, String password, long timeout, TimeUnit timeUnit) {
        return openBucket(name, password, null, timeout, timeUnit);
    }

    @Override
    public Bucket openBucket(String name, String password, List<Transcoder<? extends Document, ?>> transcoders) {
        return openBucket(name, password, transcoders, environment.connectTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public Bucket openBucket(final String name, final String password, final List<Transcoder<? extends Document, ?>> transcoders, long timeout, TimeUnit timeUnit) {
        return couchbaseAsyncCluster
            .openBucket(name, password, transcoders)
            .map(new Func1<AsyncBucket, Bucket>() {
