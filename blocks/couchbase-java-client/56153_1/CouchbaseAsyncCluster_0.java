        AsyncBucket cachedBucket = bucketCache.get(name);
        if(cachedBucket != null) {
            LOGGER.debug("Returning cached bucket {}", name);
            return Observable.just(cachedBucket);
        }

