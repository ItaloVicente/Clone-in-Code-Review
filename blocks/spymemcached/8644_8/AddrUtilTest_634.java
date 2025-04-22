  public void testThree() throws Exception {
    List<InetSocketAddress> addrs = AddrUtil
        .getAddresses(" ,  www.google.com:80 ,, ,, www.yahoo.com:81 , ,,");
    assertEquals(2, addrs.size());
    assertEquals("www.google.com", addrs.get(0).getHostName());
    assertEquals(80, addrs.get(0).getPort());
    assertEquals("www.yahoo.com", addrs.get(1).getHostName());
    assertEquals(81, addrs.get(1).getPort());
  }
