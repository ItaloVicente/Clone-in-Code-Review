
  @Test(expected = ConnectionException.class)
  public void shouldFailOnInvalidPeer() throws Exception {
    BucketMonitor monitor = new BucketMonitor(new URI("http://invalidHost:8091/"),
      BUCKET_NAME, USERNAME, PASSWORD, CONFIG_PARSER);
    monitor.startMonitor();
  }

