  private Logger logger = null;

  public LoggingTest(String name) {
    super(name);
  }

  @Override
  public void setUp() {
    logger = LoggerFactory.getLogger(getClass());
  }

  public void testDebugLogging() {
    logger.debug("debug message");
  }

  public void testInfoLogging() {
    assertTrue(logger.isInfoEnabled());
    logger.info("info message");
  }

  public void testOtherLogging() {
    logger.warn("warn message");
    logger.warn("test %s", "message");
    logger.error("error message");
    logger.error("test %s", "message");
    logger.fatal("fatal message");
    logger.fatal("test %s", "message");
    logger.log(null, "test null", null);
    assertEquals(getClass().getName(), logger.getName());
  }

  public void testLog4j() {
  }

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

  public void testMyLogger() {
    Logger l = new DefaultLogger(getClass().getName());
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

    try {
      l = new DefaultLogger(null);
      fail("Allowed me to create a logger with null name:  " + l);
    } catch (NullPointerException e) {
      assertEquals("Logger name may not be null.", e.getMessage());
