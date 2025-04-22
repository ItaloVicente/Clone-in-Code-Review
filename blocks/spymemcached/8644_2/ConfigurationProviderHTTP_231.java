        if (!pools.containsKey(DEFAULT_POOL_NAME)) {
          getLogger().warn(
              "Provided URI " + baseUri + " has no default pool... skipping");
          continue;
        }
        for (Pool pool : pools.values()) {
          URLConnection poolConnection = urlConnBuilder(baseUri, pool.getUri());
          String poolString = readToString(poolConnection);
          configurationParser.loadPool(pool, poolString);
          URLConnection poolBucketsConnection =
              urlConnBuilder(baseUri, pool.getBucketsUri());
          String sBuckets = readToString(poolBucketsConnection);
          Map<String, Bucket> bucketsForPool =
              configurationParser.parseBuckets(sBuckets);
          pool.replaceBuckets(bucketsForPool);
