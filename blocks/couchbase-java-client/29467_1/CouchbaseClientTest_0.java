  @Override
  public void testAsyncCASWithExpiration() throws Exception {
    final String key = "casWithExpiration";
    final String value = "value";

    OperationFuture<Boolean> future = client.set(key, 0, value);
    assertTrue(future.get());

    OperationFuture<CASResponse> casFuture =
      client.asyncCAS(key, future.getCas(), 1, value);
    assertEquals(CASResponse.OK, casFuture.get());

    Thread.sleep(2500);
    Object response = client.get(key);
    assertNull("casWithExpiration key should be null but was: " + response, response);
  }

