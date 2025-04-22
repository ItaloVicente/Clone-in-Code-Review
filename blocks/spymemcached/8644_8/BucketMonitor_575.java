    factory.releaseExternalResources();
  }

  protected void invalidate() {
    try {
      String response = handler.getLastResponse();
      Bucket updatedBucket = this.configParser.parseBucket(response);
      setBucket(updatedBucket);
    } catch (ParseException e) {
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.SEVERE,
          "Invalid client configuration received from server. Staying with "
          +  "existing configuration.", e);
