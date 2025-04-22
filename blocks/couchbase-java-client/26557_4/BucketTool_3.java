  public void updateBucket(final String name, final AuthType type,
    final int quota, final int replicas, final int port, final String pswd,
    final boolean flush) throws Exception {
      FunctionCallback callback = new FunctionCallback() {
        @Override
        public void callback() throws Exception {
          manager.updateBucket(name, quota, type, replicas, port, pswd, flush);
        }
        @Override
        public String success(long elapsedTime) {
          return "Bucket updation took " + elapsedTime + "ms";
        }
      };
    poll(callback);
  }

