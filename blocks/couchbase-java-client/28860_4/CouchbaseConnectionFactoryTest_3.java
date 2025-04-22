  @Test
  public void shouldBootstrapThroughProperties() throws Exception {
    System.setProperty("cbclient.nodes", "http://" + TestConfig.IPV4_ADDR
      + ":8091/pools");
    System.setProperty("cbclient.bucket", "default");
    System.setProperty("cbclient.password", "");

    CouchbaseConnectionFactory factory = new CouchbaseConnectionFactory();
    Config config = factory.getVBucketConfig();

    assertTrue(config.getServersCount() > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailIfNoNodeProperty() throws Exception {
    System.clearProperty("cbclient.nodes");
    System.setProperty("cbclient.bucket", "default");
    System.setProperty("cbclient.password", "");

    new CouchbaseConnectionFactory();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailIfNoBucketProperty() throws Exception {
    System.clearProperty("cbclient.bucket");
    System.setProperty("cbclient.password", "");
    System.setProperty("cbclient.nodes", "http://" + TestConfig.IPV4_ADDR
      + ":8091/pools");

    new CouchbaseConnectionFactory();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailIfNoPasswordProperty() throws Exception {
    System.clearProperty("cbclient.password");
    System.setProperty("cbclient.bucket", "default");
    System.setProperty("cbclient.nodes", "http://" + TestConfig.IPV4_ADDR
      + ":8091/pools");

    new CouchbaseConnectionFactory();
  }

