  public void testClient() {
    OperationException oe =
        new OperationException(OperationErrorType.CLIENT, "CLIENT_ERROR nope");
    assertSame(OperationErrorType.CLIENT, oe.getType());
    assertEquals("OperationException: CLIENT: CLIENT_ERROR nope",
        String.valueOf(oe));
  }
