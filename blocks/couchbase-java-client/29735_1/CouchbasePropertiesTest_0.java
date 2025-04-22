  @Before
  public void resetProperties() {
    System.clearProperty("viewmode");
    System.clearProperty("cbclient.viewmode");
    System.clearProperty("throttler");
    CouchbaseProperties.resetFileProperties();
  }

