  public void testNullList() throws Exception {
    String s = null;
    try {
      List<InetSocketAddress> addrs = AddrUtil.getAddresses(s);
      fail("Expected failure, got " + addrs);
    } catch (NullPointerException e) {
      assertEquals("Null host list", e.getMessage());
    }
  }
