  @Test
  public void foo() throws Exception {
    BucketMonitor m = new BucketMonitor(URI.create(STREAMING_URI),
      USERNAME, PASSWORD, CONFIG_PARSER, configProvider);

    m.startMonitor();

    Thread.sleep(TimeUnit.DAYS.toMillis(1));
  }

