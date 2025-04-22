  @Test
  public void testFlushCallback() throws Exception {
    List<URI> uris = new ArrayList<URI>();
    uris.add(URI.create("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
    CouchbaseClient client = new CouchbaseClient(uris, "default", "");

    final CountDownLatch latch = new CountDownLatch(1);
    client.flush().addListener(new OperationCompletionListener() {
      @Override
      public void onComplete(OperationFuture<?> f) throws Exception {
        latch.countDown();
      }
    });

    assertTrue(latch.await(1, TimeUnit.MINUTES));
    client.shutdown();
  }

