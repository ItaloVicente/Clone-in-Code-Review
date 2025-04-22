  public void testDebugBuffer() throws Exception {
    String input = "this is a test _";
    ByteBuffer bb = ByteBuffer.wrap(input.getBytes());
    String s = MemcachedConnection.dbgBuffer(bb, input.length());
    assertEquals("this is a test \\x5f", s);
  }
