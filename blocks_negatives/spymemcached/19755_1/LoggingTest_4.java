    l.log(null, "test null", null);
    l.log(null, "null message with exception and no requestor",
        new Exception());

    try {
      l = new DefaultLogger(null);
      fail("Allowed me to create a logger with null name:  " + l);
    } catch (NullPointerException e) {
      assertEquals("Logger name may not be null.", e.getMessage());
    }
  }

  /**
   * Test stringing levels.
   */
  public void testLevelStrings() {
    assertEquals("{LogLevel:  DEBUG}", String.valueOf(Level.DEBUG));
    assertEquals("{LogLevel:  INFO}", String.valueOf(Level.INFO));
    assertEquals("{LogLevel:  WARN}", String.valueOf(Level.WARN));
    assertEquals("{LogLevel:  ERROR}", String.valueOf(Level.ERROR));
    assertEquals("{LogLevel:  FATAL}", String.valueOf(Level.FATAL));
    assertEquals("DEBUG", Level.DEBUG.name());
    assertEquals("INFO", Level.INFO.name());
    assertEquals("WARN", Level.WARN.name());
    assertEquals("ERROR", Level.ERROR.name());
    assertEquals("FATAL", Level.FATAL.name());
