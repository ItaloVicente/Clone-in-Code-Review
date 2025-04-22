  Bucket getBucketConfiguration(String bucketname);

  void subscribe(String bucketName, Reconfigurable rec);

  void unsubscribe(String vbucketName, Reconfigurable rec);

  void shutdown();

  String getAnonymousAuthBucket();
