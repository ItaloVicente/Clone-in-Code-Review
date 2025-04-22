  Map<String, Pool> parseBase(final String base) throws ParseException;

  Map<String, Bucket> parseBuckets(String buckets) throws ParseException;

  Bucket parseBucket(String sBucket) throws ParseException;

  void loadPool(Pool pool, String sPool) throws ParseException;
