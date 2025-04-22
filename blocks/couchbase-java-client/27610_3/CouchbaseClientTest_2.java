  public void testIncrDecrZero() throws Exception {
    String k = "testIncrDecrZero";
    client.set(k, 0, "5");
    assertEquals(5, client.incr(k, 0));
    assertEquals(5, client.decr(k, 0));
  }

  public void testIncrDecrMax() throws Exception {
    String k = "testIncrDecrMax";
    client.set(k, 0, "5");
    assertEquals(client.incr(k, Integer.MAX_VALUE),Integer.MAX_VALUE+(long)5);
    client.set(k, 0, "5");
    assertEquals(0, client.decr(k, Integer.MAX_VALUE));
  }

  public void testDelNoExist() throws Exception {
    OperationFuture<Boolean> delOp = client.delete("testDelNoExist");
    assertTrue(delOp.isDone());
    assertNull(delOp.getCas());
  }

  public void testGATZeroTimeout() throws Exception {
    assertNull(client.get("gatkey"));
    assertTrue(client.set("gatkey", 1, "gatvalue").get().booleanValue());
    Thread.sleep(1500);
    assertFalse("gatvalue".equals(client.get("gatkey")));
    assertNull(client.getAndTouch("gatkey", 0));
  }

  public void testGATNegativeTimeout() throws Exception {
    assertNull(client.get("gatkey"));
    assertTrue(client.set("gatkey", -1, "gatvalue").get().booleanValue());
    Thread.sleep(2000);
    assertNotNull(client.getAndTouch("gatkey", -1));
  }

