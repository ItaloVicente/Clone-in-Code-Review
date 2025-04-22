  @Override
  public void updateBucket(final String config) {
    try {
      updateBucket(getBucket(), configurationParser.parseBucket(config));
    } catch (Exception e) {
      getLogger().info("Got new config to update, but could not decode it. "
        + "Staying with old one.");
    }
  }

