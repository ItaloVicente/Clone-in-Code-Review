  public Config getLatestConfig(String bucketname) {
    Bucket bucket = getBucketConfiguration(bucketname);
    return bucket.getConfig();
  }

  public String getAnonymousAuthBucket() {
    return ANONYMOUS_AUTH_BUCKET;
  }
