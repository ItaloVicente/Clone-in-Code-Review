  public void testObserveWithSpecialChar() throws Exception {
    String key = "special£";
    OperationFuture<Boolean> future = ((CouchbaseClient) client).set(key,
      "value", PersistTo.MASTER);
    assertTrue(future.get());
    assertTrue(future.getStatus().isSuccess());


    Map<MemcachedNode, ObserveResponse> nodes =
      ((CouchbaseClient) client).observe(key, future.getCas());
    assertFalse(nodes.isEmpty());
  }

