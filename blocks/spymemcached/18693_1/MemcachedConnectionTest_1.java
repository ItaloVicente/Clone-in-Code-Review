  
  public void testSafeExceptionHandling() throws IOException {
    MockDefaultConnectionFactory cf = new MockDefaultConnectionFactory();
	MemcachedClient client = new MemcachedClient(cf, AddrUtil.getAddresses(TestConfig.IPV4_ADDR
	           + ":" + TestConfig.PORT_NUMBER));
	 
	  
    client.set("key1", 0, 1);
    assertEquals(1, client.get("key1"));

    client.set("key2", 0, 2);
    assertEquals(2, client.get("key2"));

    client.set("key3", 0, 3);
    assertEquals(3, client.get("key3"));
  
    client.set("key4", 0, 4);
    assertEquals(4, client.get("key4"));
  }
