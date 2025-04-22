  private ConfigurationProviderHTTP configProvider;


  @Before
  public void setup() throws Exception {
    configProvider = new ConfigurationProviderHTTP(
      Arrays.asList(new URI("http://" + TestConfig.IPV4_ADDR + ":8091/pools")),
      CbTestConfig.CLUSTER_ADMINNAME,
      CbTestConfig.CLUSTER_PASS
    );
  }
