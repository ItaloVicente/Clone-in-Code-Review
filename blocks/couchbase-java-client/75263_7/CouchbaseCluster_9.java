        return openBucket(name, new ArrayList<Transcoder<? extends Document, ?>>(), timeout, timeUnit);
    }

    @Override
    public Bucket openBucket(String name, List<Transcoder<? extends Document, ?>> transcoders) {
        return openBucket(name, environment.connectTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public Bucket openBucket(final String name, List<Transcoder<? extends Document, ?>> transcoders, long timeout, TimeUnit timeUnit) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Bucket name is not allowed to be null or empty.");
        }

        Bucket cachedBucket = getCachedBucket(name);
        if (cachedBucket != null) {
            return cachedBucket;
        }

