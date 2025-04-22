
  @Test
  public void testDefaultViewTimeout() throws IOException {

    CouchbaseConnectionFactoryBuilder instance =
            new CouchbaseConnectionFactoryBuilder();
    CouchbaseConnectionFactory connFact =
      instance.buildCouchbaseConnection(uris, "default", "");

    assertEquals(75000, connFact.getViewTimeout());

  }

