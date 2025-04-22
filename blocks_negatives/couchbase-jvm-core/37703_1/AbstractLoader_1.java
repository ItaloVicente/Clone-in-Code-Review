                public BucketConfig call(final String rawConfig) {
                    try {
                        BucketConfig config = OBJECT_MAPPER.readValue(rawConfig, BucketConfig.class);
                        config.password(password);
                        return config;
                    } catch (Exception ex) {
                        throw new CouchbaseException("Could not parse configuration", ex);
                    }
