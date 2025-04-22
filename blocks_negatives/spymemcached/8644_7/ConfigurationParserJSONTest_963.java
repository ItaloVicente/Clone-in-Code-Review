    private void checkBucket(Bucket bucket) throws Exception {
        assertNotNull("Bucket is null", bucket);
        assertNotNull(bucket.getName());
        assertNotNull(bucket.getStreamingURI());
        assertNotNull(bucket.getConfig());
    }
