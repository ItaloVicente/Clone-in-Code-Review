  public void testNullNode() {
    Operation op = new TestOperation();
    assertEquals(CheckedOperationTimeoutException.class.getName()
        + ": test - failing node: <unknown>",
        new CheckedOperationTimeoutException("test", op).toString());
  }
