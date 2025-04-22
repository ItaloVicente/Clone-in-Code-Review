  private static final String REST_USER = "Administrator";
  private static final String REST_PWD = "password";
  private static final String DEFAULT_BUCKET_NAME = "default";
  private ConfigurationProviderHTTP configProvider;
  private ReconfigurableMock reconfigurable = new ReconfigurableMock();

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    List<URI> baseList =
        Arrays
            .asList(new URI("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
    configProvider = new ConfigurationProviderHTTP(baseList, REST_USER,
        REST_PWD);
    assertNotNull(configProvider);
  }

  public void testGetBucketConfiguration() throws Exception {
    Bucket bucket = configProvider.getBucketConfiguration(DEFAULT_BUCKET_NAME);
    assertNotNull(bucket);
  }

  public void testSubscribe() throws Exception {
    configProvider.subscribe(DEFAULT_BUCKET_NAME, reconfigurable);
  }

  public void testUnsubscribe() throws Exception {
    configProvider.unsubscribe(DEFAULT_BUCKET_NAME, reconfigurable);
  }

  public void testShutdown() throws Exception {
    configProvider.shutdown();
  }

  public void testGetAnonymousAuthBucket() throws Exception {
    assertEquals("default", configProvider.getAnonymousAuthBucket());
  }

  public void testBuildAuthHeader() throws UnsupportedEncodingException {
    ConfigurationProviderHTTP.buildAuthHeader("foo", "bar");
  }

  public void testBuildAuthHeaderUTF8() throws UnsupportedEncodingException {
    String result =
        ConfigurationProviderHTTP.buildAuthHeader("blahblah", "bla@@h");
    System.err.println("Authorization header for matt:this@here is " + result);
    assertEquals("Basic YmxhaGJsYWg6YmxhQEBo", result);
  }
