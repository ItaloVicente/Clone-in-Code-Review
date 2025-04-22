  public void testSetReturnsCAS() throws Exception {

    OperationFuture<Boolean> setOp = client.set("testSetReturnsCAS",
            0, "testSetReturnsCAS");
    setOp.get();
    assertTrue(setOp.getCas() > 0);
  }

