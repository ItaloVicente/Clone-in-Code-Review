        bucket = this.buckets.get(bucketname);
        Config config = null;
        if(bucket != null) {
          config = bucket.getConfig();
          if(config != null
            && config.getConfigType().equals(ConfigType.MEMCACHE)) {
            warmedUp = true;
            continue;
          }
