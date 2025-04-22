  public void updateBucket(String name, int memorySizeMB,
    AuthType authType, int replicas, int port,
    String authpassword, boolean flushEnabled) {

	try {
      BasicHttpEntityEnclosingRequest request =
      new BasicHttpEntityEnclosingRequest("POST", "/pools/default/buckets/"+name);

      StringBuilder sb = new StringBuilder();
      sb.append("&ramQuotaMB=").append(memorySizeMB);
      sb.append("&authType=").append(authType.getAuthType());
      sb.append("&replicaNumber=").append(replicas);
      sb.append("&proxyPort=").append(port);
      if (authType == AuthType.SASL) {
        sb.append("&saslPassword=").append(authpassword);
      }
      if(flushEnabled) {
        sb.append("&flushEnabled=1");
      }

	  request.setEntity(new StringEntity(sb.toString()));
	  checkError(200, sendRequest(request));
	}catch (UnsupportedEncodingException e) {
	  getLogger().error("Error creating request. Bad arguments");
      throw new RuntimeException(e);
	}
