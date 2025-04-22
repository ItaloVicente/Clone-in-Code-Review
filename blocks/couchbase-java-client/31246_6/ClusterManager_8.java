  private HttpRequest prepareRequest(final String path, final BucketType type,
    final String name, final int memorySizeMB, final AuthType authType,
    final int replicas, final int port, final String authpassword,
    final boolean flushEnabled) {
    BasicHttpEntityEnclosingRequest request =
      new BasicHttpEntityEnclosingRequest("POST", path);

    StringBuilder sb = new StringBuilder();
    sb.append("name=").append(name)
      .append("&ramQuotaMB=")
      .append(memorySizeMB)
      .append("&authType=")
      .append(authType.getAuthType())
      .append("&replicaNumber=")
      .append(replicas)
      .append("&proxyPort=")
      .append(port);
    if (type != null) {
      sb.append("&bucketType=")
        .append(type.getBucketType());
    }
    if (authType == AuthType.SASL) {
      sb.append("&saslPassword=")
        .append(authpassword);
    }
    if(flushEnabled) {
      sb.append("&flushEnabled=1");
    }
