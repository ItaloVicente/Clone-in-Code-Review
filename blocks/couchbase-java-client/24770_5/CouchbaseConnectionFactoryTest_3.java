  @Test
  public void testVBucketConfig() throws IOException , InterruptedException {
    CouchbaseConnectionFactory cf = buildFactory();
    assert  cf.getVBucketConfig() == null
      : "Couldn't retrieve VBucket Config";
    assert  cf.getVBucketConfig().getServersCount() == 0
      : "Couldn't retrieve server nodes in the configuration";
  }

  @Test
  public void testResubscriberBackOff() throws IOException {

    List<URI> baseList = new ArrayList<URI>();
    baseList.add(URI.create("http://"+"badurl"+":8091/pools"));
    ConfigurationProvider provider = new ConfigurationProviderMock();

    CouchbaseConnectionFactoryMock factory;
    factory = new CouchbaseConnectionFactoryMock(
      baseList,
      "resubscriber-bucket",
      "",
      provider
    );
    CouchbaseConnectionFactoryMock.Resubscriber resubscriber = factory.new Resubscriber(provider);
    resubscriber.run();
    assertNotNull(factory.getWaitTime());
  }
