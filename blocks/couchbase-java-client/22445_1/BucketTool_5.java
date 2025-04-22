  public void createSaslBucket(final String name, final BucketType type,
    final int quota, final int replicas) throws Exception {
    FunctionCallback callback = new FunctionCallback() {
      @Override
      public void callback() throws Exception {
        manager.createSaslBucket(type, name, quota, replicas, name);
      }

      @Override
      public String success(long elapsedTime) {
        return "Bucket creation took " + elapsedTime + "ms";
      }
    };
    poll(callback);
  }

