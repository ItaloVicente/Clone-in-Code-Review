  @Test
  public void testMemcachedConnection() throws IOException, InterruptedException {
    CouchbaseConnectionFactory cf = buildFactory();

    List<InetSocketAddress> addrs = AddrUtil.getAddressesFromURL(
      cf.getVBucketConfig().getCouchServers()
    );

    MemcachedConnection memConn = cf.createConnection(addrs);
    assertTrue(memConn.isAlive());
    memConn.shutdown();
    Thread.sleep(5000);
    assertFalse(memConn.isAlive());
  }
