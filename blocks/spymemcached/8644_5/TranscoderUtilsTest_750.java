  public void testBooleanOverflow() {
    try {
      boolean b = tu.decodeBoolean(oversizeBytes);
      fail("Got " + b + " expected assertion.");
    } catch (AssertionError e) {
    }
  }
