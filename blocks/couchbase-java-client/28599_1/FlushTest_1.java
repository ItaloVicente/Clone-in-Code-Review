  @Test
  public void testFlushOnMemcachedBucket() throws Exception {
    List<URI> uris = new ArrayList<URI>();
    uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
    CouchbaseClient client = new CouchbaseClient(uris, memcachedBucket, memcachedBucket);

    assertTrue(client.flush().get());

    client.shutdown();
  }

