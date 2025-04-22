  @Test
  public void shouldRandomizeNodeList() throws Exception {
    ConfigurationProviderMemcacheMock providerMock = new ConfigurationProviderMemcacheMock(
      Arrays.asList("127.0.0.1:8091/pools", "127.0.0.2:8091/pools",
        "127.0.0.3:8091/pools", "127.0.0.4:8091/pools")
    );

    CouchbaseConnectionFactory connFact = new CouchbaseConnectionFactoryMock(
      Arrays.asList(
      ), "default", "", providerMock, CouchbaseNodeOrder.RANDOM
    );

    List<URI> oldList = connFact.getStoredBaseList();

    int tries = 100;
    for(int i = 0; i < tries; i++) {
      connFact.updateStoredBaseList(connFact.getVBucketConfig());
      assertTrue(providerMock.baseListUpdated);
      List<URI> newList = connFact.getStoredBaseList();
      System.out.println("old: " + oldList);
      System.out.println("new: " + newList);
      if (oIndex1 != nIndex1 || oIndex2 != nIndex2 || oIndex3 != nIndex3 || oIndex4 == nIndex4) {
        assertTrue(true);
        return;
      }
    }

    assertTrue("Node list was not different after " + tries + " tries", false);
  }

