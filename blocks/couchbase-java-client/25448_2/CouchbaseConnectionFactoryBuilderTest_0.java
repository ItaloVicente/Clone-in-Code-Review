  @Test
  public void testIdenticalConfigs() throws Exception {
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();

    CouchbaseConnectionFactory connFact =
      instance.buildCouchbaseConnection(uris, "default", "");

    CouchbaseConnectionFactory directFact =
      new CouchbaseConnectionFactory(uris, "default", "");

    assertEquals(connFact.getFailureMode(), directFact.getFailureMode());
    assertEquals(connFact.getHashAlg(), directFact.getHashAlg());
  }

