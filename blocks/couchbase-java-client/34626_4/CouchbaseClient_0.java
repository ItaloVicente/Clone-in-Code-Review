  @Override
  public void connectionLost(SocketAddress sa) {
    getLogger().debug("Connection lost for node: " + sa);
    cbConnFactory.configurationProvider.reloadConfig();
    super.connectionLost(sa);
  }

