  public void testEmptyVarargConstructor() throws Exception {
    try {
      client = new MemcachedClient();
      fail("Expected illegal arg exception, got " + client);
    } catch (IllegalArgumentException e) {
      assertArgRequired(e);
    }
  }

