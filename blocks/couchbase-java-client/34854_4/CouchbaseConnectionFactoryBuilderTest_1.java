  @Test
  public void testSetAuthWaitTime() throws Exception {
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();
    instance.setAuthWaitTime(5000);

    CouchbaseConnectionFactory factory =
      instance.buildCouchbaseConnection(uris, "default", "");
    assertEquals(5000, factory.getAuthWaitTime());
  }

