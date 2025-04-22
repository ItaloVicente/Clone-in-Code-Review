  public void testAsyncCASObserve() throws Exception {
    CouchbaseClient client = (CouchbaseClient) this.client;
    String key = "asyncCASObserve";

    OperationFuture<Boolean> setFuture = client.set(key, "value",
      PersistTo.MASTER);
    assertTrue(setFuture.get());

    final CountDownLatch latch = new CountDownLatch(1);
    client.asyncCas(key, setFuture.getCas(), "value2", PersistTo.MASTER)
      .addListener(new OperationCompletionListener() {
        @Override
        public void onComplete(OperationFuture<?> future) throws Exception {
          System.out.println(future.getStatus());
          if (future.getStatus().isSuccess()) {
            latch.countDown();
          }
        }
      }
    );

    assertTrue(latch.await(50, TimeUnit.SECONDS));
  }

