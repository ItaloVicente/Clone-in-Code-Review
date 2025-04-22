    return rv;
  }

  @Override
  public List<String> getServers() {
    return servers;
  }

  @Override
  public List<VBucket> getVbuckets() {
    return vbuckets;
  }

  @Override
  public ConfigDifference compareTo(Config config) {
    ConfigDifference difference = new ConfigDifference();

    if (this.serversCount == config.getServersCount()) {
      difference.setSequenceChanged(false);
      for (int i = 0; i < this.serversCount; i++) {
        if (!this.getServer(i).equals(config.getServer(i))) {
          difference.setSequenceChanged(true);
          break;
