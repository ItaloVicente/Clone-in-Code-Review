    final List<URI> uris = Arrays.asList(URI.create("http://"
        + TestConfig.IPV4_ADDR + ":8091/pools"));
    
    BucketTool bucketTool = new BucketTool();
    bucketTool.deleteAllBuckets();
    bucketTool.createDefaultBucket(BucketType.COUCHBASE, 256, 0);

    BucketTool.FunctionCallback callback = new FunctionCallback() {
      @Override
      public void callback() throws Exception {
        initClient(new CouchbaseConnectionFactory(uris, "default", ""));
      }
