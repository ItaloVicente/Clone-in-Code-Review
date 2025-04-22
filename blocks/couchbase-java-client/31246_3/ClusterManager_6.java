    List<String> buckets = listBuckets();
    if(buckets.contains(name)){
      throw new RuntimeException("Bucket with given name already exists");
    } else {
      HttpRequest request = prepareRequest(BUCKETS, type, name, memorySizeMB,
        authType, replicas, port, authpassword, flushEnabled);
      checkForErrorCode(202, sendRequest(request));
