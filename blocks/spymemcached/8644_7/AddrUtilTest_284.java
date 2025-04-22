  public void testSingle() throws Exception {
    List<InetSocketAddress> addrs = AddrUtil.getAddresses("www.google.com:80");
    assertEquals(1, addrs.size());
    assertEquals("www.google.com", addrs.get(0).getHostName());
    assertEquals(80, addrs.get(0).getPort());
  }
