  public void testBrokenHost() throws Exception {
    String s = "www.google.com:80 www.yahoo.com:81:more";
    try {
      List<InetSocketAddress> addrs = AddrUtil.getAddresses(s);
      fail("Expected failure, got " + addrs);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      assertEquals("For input string: \"more\"", e.getMessage());
    }
  }
