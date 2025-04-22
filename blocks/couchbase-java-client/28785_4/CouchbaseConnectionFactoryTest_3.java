  @Test
  public void shouldRandomizeNodeList() throws Exception {
    instance.setStreamingNodeOrder(CouchbaseNodeOrder.RANDOM);
    ConfigurationProviderMemcacheMock providerMock = new ConfigurationProviderMemcacheMock(
      Arrays.asList("127.0.0.1:8091/pools", "127.0.0.2:8091/pools",
        "127.0.0.3:8091/pools", "127.0.0.4:8091/pools")
    );

    CouchbaseConnectionFactory connFact = new CouchbaseConnectionFactoryMock(
      Arrays.asList(
        new URI("http://127.0.0.1:8091/pools"), new URI("http://127.0.0.2:8091/pools"),
        new URI("http://127.0.0.3:8091/pools"), new URI("http://127.0.0.5:8091/pools")
      ), "default", "", providerMock, CouchbaseNodeOrder.RANDOM
    );

    List<URI> oldList = connFact.getStoredBaseList();
    int oldIndex = oldList.indexOf(new URI("http://127.0.0.1:8091/pools"));

    int tries = 10;
    for(int i = 0; i < tries; i++) {
      connFact.updateStoredBaseList(connFact.getVBucketConfig());
      assertTrue(providerMock.baseListUpdated);
      List<URI> newList = connFact.getStoredBaseList();
      int newIndex = newList.indexOf(new URI("http://127.0.0.1:8091/pools"));
      if (oldIndex != newIndex) {
        assertTrue(true);
        return;
      }
    }

    assertTrue("Node list was not different after " + tries + " tries", false);
  }

