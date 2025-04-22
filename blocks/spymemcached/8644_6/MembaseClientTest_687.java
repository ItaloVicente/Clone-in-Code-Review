  @Override
  protected void initClient() throws Exception {
    initClient(new MembaseConnectionFactory(Arrays.asList(URI.create("http://"
        + TestConfig.IPV4_ADDR + ":8091/pools")), "default", "default", ""));
  }

  @Override
  protected String getExpectedVersionSource() {
    if (TestConfig.IPV4_ADDR.equals("127.0.0.1")) {
      return "localhost/127.0.0.1:11210";
    }
    return TestConfig.IPV4_ADDR + ":11210";
  }

  @Override
  protected void initClient(ConnectionFactory cf) throws Exception {
    client = new MembaseClient((MembaseConnectionFactory) cf);
  }

  @Override
  public void testAvailableServers() {
    try {
      Thread.sleep(10); // Let the client warm up
    } catch (InterruptedException e) {
      fail("Interrupted while client was warming up");
    }
    assert client.getAvailableServers().size() == 2;
  }

  protected void syncGetTimeoutsInitClient() throws Exception {
    initClient(new MembaseConnectionFactory(Arrays.asList(URI
        .create("http://localhost:8091/pools")), "default", "default", "") {
      @Override
      public long getOperationTimeout() {
        return 2;
      }

      @Override
      public int getTimeoutExceptionThreshold() {
        return 1000000;
      }
    });
  }
