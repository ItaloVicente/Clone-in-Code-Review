    int serversCount = servers.length();
    JSONArray vbuckets = jsonObject.getJSONArray("vBucketMap");
    int vbucketsCount = vbuckets.length();
    if (vbucketsCount == 0 || (vbucketsCount & (vbucketsCount - 1)) != 0) {
      throw new ConfigParsingException("Number of buckets must be a power of "
        + "two, > 0 and <= " + VBucket.MAX_BUCKETS);
