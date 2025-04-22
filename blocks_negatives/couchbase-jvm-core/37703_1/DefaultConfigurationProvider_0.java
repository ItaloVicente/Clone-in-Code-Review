        try {
            BucketConfig config = OBJECT_MAPPER.readValue(rawConfig, BucketConfig.class);
            config.password(currentConfig.get().bucketConfig(bucket).password());
            upsertBucketConfig(bucket, config);
        } catch (Exception ex) {
            throw new CouchbaseException("Could not parse configuration", ex);
