  public void testParseBucketsClustered() throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append(BUCKETS_CLUSTER_STRING);
    sb.append(BUCKETS_CLUSTER_STRING2);
    Map<String, Bucket> buckets = configParser.parseBuckets(sb.toString());
    for (Bucket bucket : buckets.values()) {
      checkBucket(bucket);
