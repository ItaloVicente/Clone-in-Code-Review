  public void testSetWithCallback() throws Exception {
    OperationFuture<Boolean> setOp =
      client.set("setWithCallback", 0, "content");

    final CountDownLatch latch = new CountDownLatch(1);
    setOp.addListener(new OperationFutureListener() {
      @Override
      public void onComplete(OperationFuture<?> f) throws Exception {
        latch.countDown();
      }
    });

    assertTrue(latch.await(2, TimeUnit.SECONDS));
  }

  public void testGetWithCallback() throws Exception {
    client.set("getWithCallback", 0, "content").get();

    GetFuture<Object> getOp = client.asyncGet("getWithCallback");

    final CountDownLatch latch = new CountDownLatch(1);
    getOp.addListener(new GetFutureListener() {
      @Override
      public void onComplete(GetFuture<?> f) throws Exception {
        latch.countDown();
      }
    });

    assertTrue(latch.await(2, TimeUnit.SECONDS));
  }

  public void testGetBulkWithCallback() throws Exception {
    client.set("getBulkWithCallback1", 0, "content").get();
    BulkFuture<Map<String, Object>> asyncGetBulk =
      client.asyncGetBulk("getBulkWithCallback1");

    final CountDownLatch latch = new CountDownLatch(1);
    asyncGetBulk.addListener(new BulkGetFutureListener() {
      @Override
      public void onComplete(BulkGetFuture<?> f) throws Exception {
        latch.countDown();
      }
    });

    assertTrue(latch.await(2, TimeUnit.SECONDS));
  }

