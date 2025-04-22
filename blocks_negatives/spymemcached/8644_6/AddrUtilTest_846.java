	public void testBrokenList2() throws Exception {
		String s="   ";
		try {
			List<InetSocketAddress> addrs=AddrUtil.getAddresses(s);
			fail("Expected failure, got " + addrs);
		} catch(IllegalArgumentException e) {
			assertEquals("No hosts in list:  ``   ''", e.getMessage());
		}
	}
