  @Test
  public void testAssertions() {
    boolean caught = false;
    try {
      assert false;
    } catch (AssertionError e) {
      caught = true;
    }
    assertTrue("Assertions are not enabled!", caught);
  }

