  public void testAppendWithoutCAS() throws Exception {
    final String key = "append.key";
    assertTrue(client.set(key, 5, "test").get());
    OperationFuture<Boolean> op = client.append(key, "es");
    assertTrue(op.get());
    assert op.getStatus().isSuccess();
    assertEquals("testes", client.get(key));
  }

  public void testPrependWithoutCAS() throws Exception {
    final String key = "prepend.key";
    assertTrue(client.set(key, 5, "test").get());
    OperationFuture<Boolean> op = client.prepend(key, "es");
    assertTrue(op.get());
    assert op.getStatus().isSuccess();
    assertEquals("estest", client.get(key));
  }

