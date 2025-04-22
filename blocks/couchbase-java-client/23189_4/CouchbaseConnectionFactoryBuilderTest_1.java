
  @Test
  public void testDefaultValues() throws IOException {

    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();

    CouchbaseConnectionFactory connFact =
      instance.buildCouchbaseConnection(uris, "default", "");


    assertEquals(CouchbaseConnectionFactory.DEFAULT_VIEW_TIMEOUT,
      connFact.getViewTimeout());
    assertEquals(CouchbaseConnectionFactory.DEFAULT_OBS_POLL_INTERVAL,
      connFact.getObsPollInterval());
    assertEquals(CouchbaseConnectionFactory.DEFAULT_OBS_POLL_MAX,
      connFact.getObsPollMax());
    assertEquals(CouchbaseConnectionFactory.DEFAULT_MIN_RECONNECT_INTERVAL,
      connFact.getMinReconnectInterval());
  }

