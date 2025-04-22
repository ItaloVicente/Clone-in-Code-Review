      boolean warmedUp = false;
      int maxBackoffRetries = 5;
      int retryCount = 1;
      while(warmedUp == false) {
        readPools(bucketname);
        Config config = this.buckets.get(bucketname).getConfig();
        System.out.println("VBUCKET COUNT IS: " + config.getVbucketsCount());
        if(config.getVbucketsCount() == 0) {
          if(retryCount >= maxBackoffRetries) {
            throw new ConfigurationException("Cluster is not in a warmed up "
              + "state after " + maxBackoffRetries + " exponential retries.");
          }
          int backoffSeconds = new Double(
            retryCount * Math.pow(2, retryCount++)).intValue();
          getLogger().info("Cluster is currently warming up, waiting "
            + backoffSeconds + " seconds for vBuckets to show up.");
          try {
            Thread.sleep(backoffSeconds * 1000);
          } catch (InterruptedException ex) {
            throw new ConfigurationException("Cluster is not in a warmed up "
              + "state.");
          }
        } else {
          warmedUp = true;
        }
      }
