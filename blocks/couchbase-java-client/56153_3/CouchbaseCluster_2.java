        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Bucket name is not allowed to be null or empty.");
        }

        Bucket cachedBucket = bucketCache.get(name);
        if(cachedBucket != null) {
            if (cachedBucket.isClosed()) {
                LOGGER.debug("Not returning cached bucket \"{}\", because it is closed.", name);
                bucketCache.remove(name);
            } else {
                LOGGER.debug("Returning still open, cached bucket \"{}\"", name);
                return cachedBucket;
            }
        }
