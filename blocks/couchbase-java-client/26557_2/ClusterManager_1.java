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
      }

      try {
        request.setEntity(new StringEntity(sb.toString()));
        System.out.println(request.toString());
      } catch (UnsupportedEncodingException e) {
        getLogger().error("Error creating request. Bad arguments");
        throw new RuntimeException(e);
      }

      checkError(202, sendRequest(request));
