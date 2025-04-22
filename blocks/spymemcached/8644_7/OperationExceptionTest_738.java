  public void testServer() {
    OperationException oe = new OperationException(OperationErrorType.SERVER,
        "SERVER_ERROR figures");
    assertSame(OperationErrorType.SERVER, oe.getType());
    assertEquals("OperationException: SERVER: SERVER_ERROR figures",
        String.valueOf(oe));
  }
