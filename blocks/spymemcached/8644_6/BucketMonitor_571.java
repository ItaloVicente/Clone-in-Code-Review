    createChannel();
    this.handler = channel.getPipeline().get(BucketUpdateResponseHandler.class);
    handler.setBucketMonitor(this);
    HttpRequest request = prepareRequest(cometStreamURI, host);
    channel.write(request);
    try {
      String response = this.handler.getLastResponse();
      logFiner("Getting server list returns this last chunked response:\n"
          + response);
      Bucket bucketToMonitor = this.configParser.parseBucket(response);
      setBucket(bucketToMonitor);
    } catch (ParseException ex) {
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
        "Invalid client configuration received from server. Staying with "
        + "existing configuration.", ex);
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINE,
        "Invalid client configuration received:\n" + handler.getLastResponse()
        + "\n");
