        manager.createDefaultBucket(type, quota, replicas, flush);
      }

      @Override
      public String success(long elapsedTime) {
        return "Bucket creation took " + elapsedTime + "ms";
      }
    };
    poll(callback);
  }

  public void createSaslBucket(final String name, final BucketType type,
    final int quota, final int replicas, final boolean flush) throws Exception {
    FunctionCallback callback = new FunctionCallback() {
      @Override
      public void callback() throws Exception {
        manager.createSaslBucket(type, name, quota, replicas, name, flush);
