  public void testDeleteWithCAS() throws Exception {
    final String key = "delete.with.cas";
    final long wrongCAS = 1234;

    OperationFuture<Boolean> setFuture = client.set(key, 0, "test");
    assertTrue(setFuture.get());

    assertFalse(client.delete(key, wrongCAS).get());
    assertTrue(client.delete(key, setFuture.getCas()).get());
    assertNull(client.get(key));
  }

