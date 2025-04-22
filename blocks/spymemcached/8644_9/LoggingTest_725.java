  }

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
  }

  public void testExceptionArg() throws Exception {
    Object[] args = new Object[] { "a", 42, new Exception("test") };
    Throwable t = ((AbstractLogger) logger).getThrowable(args);
    assertNotNull(t);
    assertEquals("test", t.getMessage());
  }

  public void testNoExceptionArg() throws Exception {
    Object[] args = new Object[] { "a", 42, new Exception("test"), "x" };
    Throwable t = ((AbstractLogger) logger).getThrowable(args);
    assertNull(t);
  }
