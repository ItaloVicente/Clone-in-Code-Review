
  @Test
  public void testDefaultViewTimeout() throws IOException {

    CouchbaseConnectionFactoryBuilder instance =
            new CouchbaseConnectionFactoryBuilder();
    CouchbaseConnectionFactory cfb = instance.buildCouchbaseConnection(uris, "default", "");

    assertEquals(75000, cfb.getViewTimeout());

  }

