    BucketTool.FunctionCallback callback = new BucketTool.FunctionCallback() {
      @Override
      public void callback() throws Exception {
        initClient(new CouchbaseConnectionFactory(uris, "default", ""));
      }

      @Override
      public String success(long elapsedTime) {
        return "Client Initialization took " + elapsedTime + "ms";
      }
    };
    bucketTool.poll(callback);
    bucketTool.waitForWarmup(client);

    tclient = new TestingCouchbaseClient(
      new FailInjectingCouchbaseConnectionFactory(uris, "default", "")
    );
