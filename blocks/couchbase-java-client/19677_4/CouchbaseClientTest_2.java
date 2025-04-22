  @Override
  public void testGracefulShutdown() throws Exception {
    for (int i = 0; i < 1000; i++) {
      client.set("t" + i, 10, i);
    }
    assertTrue("Couldn't shut down within five seconds",
        client.shutdown(5, TimeUnit.SECONDS));
    initClient(new CouchbaseConnectionFactory(
          Arrays.asList(URI.create("http://"
          + TestConfig.IPV4_ADDR + ":8091/pools")), "default", ""));
    Collection<String> keys = new ArrayList<String>();
    for (int i = 0; i < 1000; i++) {
      keys.add("t" + i);
    }
    Map<String, Object> m = client.getBulk(keys);
    assertEquals(1000, m.size());
    for (int i = 0; i < 1000; i++) {
      assertEquals(i, m.get("t" + i));
    }
  }

