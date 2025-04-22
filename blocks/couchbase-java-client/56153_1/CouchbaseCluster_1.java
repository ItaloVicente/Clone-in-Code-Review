        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Bucket name is not allowed to be null or empty.");
        }

        Bucket cachedBucket = bucketCache.get(name);
        if(cachedBucket != null) {
            LOGGER.debug("Returning cached bucket {}", name);
            return cachedBucket;
        }
