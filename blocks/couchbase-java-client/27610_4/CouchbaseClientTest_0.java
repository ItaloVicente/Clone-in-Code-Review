  public void testDelReturnsCAS() throws Exception {
    OperationFuture<Boolean> setOp = client.set("testDelReturnsCAS", 0, EMPTY);
    assertTrue(setOp.isDone());
    OperationFuture<Boolean> delOp = client.delete("testDelReturnsCAS");
    assertTrue(delOp.getCas() > 0);
  }

