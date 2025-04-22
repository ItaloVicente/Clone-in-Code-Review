                                            final CouchbaseBucketConfig config) {
        if (!(request instanceof AbstractKeyAwareBinaryRequest))
        {
            throw new IllegalStateException("Request ist not key aware: " + request);
        }
        final String key = ((AbstractKeyAwareBinaryRequest) request).key();
