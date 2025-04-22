
  @Test
  public void testSetViewTimeout() throws IOException {
    int viewTimeout = 30000;
    int lowTimeout = 200;
    int lowerTimeoutLimit = 500;

    CouchbaseConnectionFactoryBuilder instance =
      new CouchbaseConnectionFactoryBuilder();
     CouchbaseConnectionFactoryBuilder instanceResult
      = instance.setViewTimeout(viewTimeout);
    assertEquals(instance, instanceResult);
    assertEquals(viewTimeout, instanceResult.getViewTimeout());

    instanceResult = instance.setViewTimeout(lowTimeout);
    assertEquals(lowerTimeoutLimit, instanceResult.getViewTimeout());

    instance.buildCouchbaseConnection(uris, "default", "");
  }
