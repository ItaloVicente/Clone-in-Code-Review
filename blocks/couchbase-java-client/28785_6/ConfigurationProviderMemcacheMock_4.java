  private List<String> nodeList;
  public boolean baseListUpdated;

  public ConfigurationProviderMemcacheMock(List<String> nodeList) {
    this.nodeList = nodeList;
    baseListUpdated = false;
  }

  public ConfigurationProviderMemcacheMock() {
    this(Arrays.asList(TestConfig.IPV4_ADDR+":8091"));
  }

