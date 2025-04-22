  public void updateBucket(final String name, final int memorySizeMB,
    final AuthType authType, final int replicas, final int port,
    final String authpassword, final boolean flushEnabled) {

    List<String> buckets = listBuckets();
    if (buckets.contains(name)) {
      HttpRequest request = prepareRequest(BUCKETS + name, null, name,
        memorySizeMB, authType, replicas, port, authpassword, flushEnabled);
      checkForErrorCode(200, sendRequest(request));
    } else {
      throw new RuntimeException("Bucket with given name already does not "
        + "exist");
    }
  }
