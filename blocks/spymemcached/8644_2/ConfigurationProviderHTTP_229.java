  public Bucket getBucketConfiguration(final String bucketname) {
    if (bucketname == null || bucketname.isEmpty()) {
      throw new IllegalArgumentException("Bucket name can not be blank.");
    }
    Bucket bucket = this.buckets.get(bucketname);
    if (bucket == null) {
      readPools(bucketname);
