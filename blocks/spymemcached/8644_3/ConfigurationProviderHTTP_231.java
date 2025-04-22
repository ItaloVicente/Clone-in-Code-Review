        }
        boolean bucketFound = false;
        for (Pool pool : pools.values()) {
          if (pool.hasBucket(bucketToFind)) {
            bucketFound = true;
            break;
          }
        }
        if (bucketFound) {
          for (Pool pool : pools.values()) {
            for (Map.Entry<String, Bucket> bucketEntry : pool.getROBuckets()
                .entrySet()) {
              this.buckets.put(bucketEntry.getKey(), bucketEntry.getValue());
