  private  void createBucket(BucketType type, String name,
      int memorySizeMB, AuthType authType, int replicas, int port,
      String authpassword, boolean flushEnabled) {

      List<String> buckets = listBuckets();
      if(buckets.contains(name)){
        throw new RuntimeException("Bucket with given name already exists");
      } else {
      BasicHttpEntityEnclosingRequest request =
        new BasicHttpEntityEnclosingRequest("POST", "/pools/default/buckets");

      StringBuilder sb = new StringBuilder();
      sb.append("name=").append(name);
      sb.append("&ramQuotaMB=").append(memorySizeMB);
      sb.append("&authType=").append(authType.getAuthType());
      sb.append("&replicaNumber=").append(replicas);
      sb.append("&bucketType=").append(type.getBucketType());
      sb.append("&proxyPort=").append(port);
      if (authType == AuthType.SASL) {
        sb.append("&saslPassword=").append(authpassword);
      }
      if(flushEnabled) {
        sb.append("&flushEnabled=1");
      }
