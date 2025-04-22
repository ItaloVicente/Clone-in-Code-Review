
  @Test
  public void testopTimeOut() throws IOException {
    int opTimeout = 999;
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();
    CouchbaseConnectionFactory cf =
            instance.buildCouchbaseConnection(uris, "default", "", "");
    assertEquals("Did the test run with opTimeout=999",
            999L, cf.getOperationTimeout());
    CouchbaseConnectionFactoryBuilder instanceResult
      = (CouchbaseConnectionFactoryBuilder)
            instance.setOpTimeout(opTimeout);
    assertEquals(opTimeout,
            instanceResult.build().getOperationTimeout());
    instance.buildCouchbaseConnection(uris, "default", "");
  }

  @Test
  public void testTimeoutExceptionThreshold() throws IOException {
    int timeoutExceptionThreshold = 9999;
    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();
    CouchbaseConnectionFactory cf =
            instance.buildCouchbaseConnection(uris, "default", "", "");
    assertEquals("Did the test run with timeoutExceptionThreshold=999",
            999L, cf.getTimeoutExceptionThreshold());
    CouchbaseConnectionFactoryBuilder instanceResult
      = (CouchbaseConnectionFactoryBuilder)
            instance.setTimeoutExceptionThreshold(
            timeoutExceptionThreshold + 2);
    assertEquals(timeoutExceptionThreshold,
            instanceResult.build().getTimeoutExceptionThreshold());
    instance.buildCouchbaseConnection(uris, "default", "");
  }
