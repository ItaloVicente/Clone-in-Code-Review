  @Test
  public void testSetObsTimeout() throws IOException {
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();

    int timeout = 7500;
    int interval = 40;
    instance.setObsTimeout(timeout);
    instance.setObsPollInterval(interval);

    CouchbaseConnectionFactory connFact =
      instance.buildCouchbaseConnection(uris, "default", "");

    assertEquals(timeout, connFact.getObsTimeout());
    assertEquals(interval, connFact.getObsPollInterval());

    int expected = (timeout / interval) + 1; // rounding
    assertEquals(expected, connFact.getObsPollMax());
  }

