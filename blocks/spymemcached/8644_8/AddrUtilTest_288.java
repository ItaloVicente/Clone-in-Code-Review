  public void testBrokenHost2() throws Exception {
    String s = "www.google.com:80 www.yahoo.com";
    try {
      List<InetSocketAddress> addrs = AddrUtil.getAddresses(s);
      fail("Expected failure, got " + addrs);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid server ``www.yahoo.com'' in list:  " + s,
          e.getMessage());
    }
  }
