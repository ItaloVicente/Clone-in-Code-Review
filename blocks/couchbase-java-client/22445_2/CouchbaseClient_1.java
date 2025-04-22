
  public boolean flushBucket() {
    FlushResponse res = cbConnFactory.getClusterManager().flushBucket(
      cbConnFactory.getBucketName());
    return res.equals(FlushResponse.OK);
  }

