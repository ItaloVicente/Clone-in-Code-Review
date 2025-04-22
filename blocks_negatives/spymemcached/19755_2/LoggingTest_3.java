  public void testLog4j() {
  }

  /**
   * Test the sun logger.
   */
  public void testSunLogger() {
    Logger l = new SunLogger(getClass().getName());
    assertFalse(l.isDebugEnabled());
    l.debug("debug message");
    assertTrue(l.isInfoEnabled());
    l.info("info message");
    l.warn("warn message");
    l.error("error message");
    l.fatal("fatal message");
    l.fatal("fatal message with exception", new Exception());
    l.log(null, "test null", null);
    l.log(null, "null message with exception and no requestor",
        new Exception());
  }

  /**
   * Test the default logger.
   */
  public void testMyLogger() {
    Logger l = new DefaultLogger(getClass().getName());
