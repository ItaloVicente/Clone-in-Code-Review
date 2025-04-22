    if (bucket.isNotUpdating()) {
      getLogger().info("Bucket configuration is disconnected from cluster "
        + "configuration updates, attempting to reconnect.");
      CouchbaseConnectionFactory cbcf = (CouchbaseConnectionFactory)connFactory;
      cbcf.requestConfigReconnect(cbcf.getBucketName(), this);
      cbcf.checkConfigUpdate();
    }
