  public void testMultipleOperation() {
    Collection<Operation> ops = new ArrayList<Operation>();
    ops.add(buildOp(11211));
    ops.add(buildOp(64212));
    assertEquals(CheckedOperationTimeoutException.class.getName()
        + ": test - failing nodes: " + TestConfig.IPV4_ADDR + ":11211, "
        + TestConfig.IPV4_ADDR + ":64212",
        new CheckedOperationTimeoutException("test", ops).toString());
  }
