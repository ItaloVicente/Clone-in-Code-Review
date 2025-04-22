  @Test
  public void testViewLoadWithListener() throws Exception {
    final CountDownLatch latch = new CountDownLatch(1);
    client.asyncGetView(DESIGN_DOC_WO_REDUCE, VIEW_NAME_W_REDUCE).addListener(
      new HttpCompletionListener() {
      @Override
      public void onComplete(HttpFuture<?> httpFuture) throws Exception {
        if (httpFuture.getStatus().isSuccess()) {
          latch.countDown();
        }
      }
    });
    assertTrue(latch.await(1, TimeUnit.MINUTES));
  }

