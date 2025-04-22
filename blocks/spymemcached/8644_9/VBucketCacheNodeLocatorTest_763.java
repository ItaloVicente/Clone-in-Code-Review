  public void testGetConfig() {
    ConfigFactory configFactory = new DefaultConfigFactory();
    Config config = configFactory.create(CONFIG_IN_ENVELOPE);
    config.getServersCount();
    List<String> servers = config.getServers();
    System.out.println(servers);
  }
