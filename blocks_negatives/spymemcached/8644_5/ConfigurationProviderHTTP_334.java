    /**
     * Connects to the REST service and retrieves the bucket configuration from the first pool available
     * @param bucketname bucketname
     * @return vbucket configuration
     * @throws ConfigurationException
     */
    public Bucket getBucketConfiguration(final String bucketname) throws ConfigurationException {
        if (bucketname == null || bucketname.isEmpty()) {
            throw new IllegalArgumentException("Bucket name can not be blank.");
        }
        Bucket bucket = this.buckets.get(bucketname);
        if (bucket == null) {
            readPools(bucketname);
        }
        return this.buckets.get(bucketname);
