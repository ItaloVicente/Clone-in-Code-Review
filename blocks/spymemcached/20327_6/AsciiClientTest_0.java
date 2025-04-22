
  public void testAsyncCASResponse() throws InterruptedException,
    ExecutionException {
    String key = "testAsyncCASResponse";
    client.set(key, 300, key + "0");
    CASValue<Object> getsRes = client.gets(key);
    OperationFuture<CASResponse> casRes = client.asyncCAS(key, getsRes.getCas(),
      key + "1");
    CASResponse innerCasRes = casRes.get();
    try {
      casRes.getCas();
      fail("Expected an UnsupportedOperationException");
    } catch (UnsupportedOperationException ex) {
    }
  }

