  public void testGeneral() {
    OperationException oe =
        new OperationException(OperationErrorType.GENERAL, "GENERAL wtf");
    assertSame(OperationErrorType.GENERAL, oe.getType());
    assertEquals("OperationException: GENERAL", String.valueOf(oe));
  }
