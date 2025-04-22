
  public boolean flushBucket() {
    ClusterManager cm = new ClusterManager(
      cbConnFactory.getBaseList(),
      cbConnFactory.getBucketName(),
      cbConnFactory.getPassword()
    );

    FlushResponse res = cm.flushBucket(cbConnFactory.getBucketName());
    return res.equals(FlushResponse.OK);
  }
