  public void testAsyncIncrementWithDefault() throws Exception {
    String k = "async-incr-with-default";
    OperationFuture<Long> f = client.asyncIncr(k, 1, 5);
    assertEquals(StatusCode.SUCCESS, f.getStatus().getStatusCode());
    assertEquals(5, (long) f.get());

    f = client.asyncIncr(k, 1, 5);
    assertEquals(StatusCode.SUCCESS, f.getStatus().getStatusCode());
    assertEquals(6, (long) f.get());
  }

  public void testAsyncDecrementWithDefault() throws Exception {
    String k = "async-decr-with-default";
    OperationFuture<Long> f = client.asyncDecr(k, 4, 10);
    assertEquals(StatusCode.SUCCESS, f.getStatus().getStatusCode());
    assertEquals(10, (long) f.get());

    f = client.asyncDecr(k, 4, 10);
    assertEquals(StatusCode.SUCCESS, f.getStatus().getStatusCode());
    assertEquals(6, (long) f.get());
  }

