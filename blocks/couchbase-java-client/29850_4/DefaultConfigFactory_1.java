    final JSONArray vBuckets = vBucketServerMap.getJSONArray("vBucketMap");
    final int vBucketsCount = vBuckets.length();
    if (vBucketsCount == 0 || (vBucketsCount & (vBucketsCount - 1)) != 0) {
      throw new ConfigParsingException("Number of vBuckets must be a power of "
        + "two, > 0 and <= " + VBucket.MAX_BUCKETS + " (got " + vBucketsCount
        + ")");
    }
