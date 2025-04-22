  @Override
  public void updateBucket(final String config) {
    try {
      updateBucket(getBucket(), configurationParser.parseBucket(config));
    } catch (Exception ex) {
      getLogger().warn("Got new config to update, but could not decode it. "
        + "Staying with old one.", ex);
      getLogger().debug("Problematic config is:" + config);
    }
  }

