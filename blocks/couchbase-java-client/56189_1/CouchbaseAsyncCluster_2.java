            })
            .onErrorResumeNext(new OpenBucketErrorHandler(name));
    }

    private AsyncBucket getCachedBucket(final String name) {
        AsyncBucket cachedBucket = bucketCache.get(name);

        if(cachedBucket != null) {
            if (cachedBucket.isClosed()) {
                LOGGER.debug(
                    "Not returning cached async bucket \"{}\", because it is closed.",
                    name
                );
                bucketCache.remove(name);
            } else {
                LOGGER.debug("Returning still open, cached async bucket \"{}\"", name);
                return cachedBucket;
            }
        }

        return null;
