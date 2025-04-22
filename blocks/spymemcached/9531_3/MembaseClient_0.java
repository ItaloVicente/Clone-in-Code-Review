  @Override
  public int getNumVBuckets() {
    return ((MembaseConnectionFactory)connFactory).getVBucketConfig()
      .getVbucketsCount();
  }

