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
