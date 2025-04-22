  public void testAsyncCASResponse() {
    String key = "testAsyncCASResponse";
    client.set(key, 300, key + "0");
    CASValue<Object> getsRes = client.gets(key);
    OperationFuture<CASResponse> casRes = client.asyncCAS(key, getsRes.getCas(),
      key + "1");
    try {
      CASResponse innerCasRes = casRes.get();
      assertNotNull("OperationFuture is missing cas value.", casRes.getCas());
    } catch (InterruptedException ex) {
      fail("Interrupted while getting CASResponse");
    } catch (ExecutionException ex) {
      fail("Execution problem while getting CASResponse");
    }
    assertNotNull(casRes.getCas());
  }

