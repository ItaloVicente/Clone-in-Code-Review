
	@Test
	public void testWhitespace() throws Exception {
		config("Host foo \tbar   baz\nPort 29418\n");
		Host h = osc.lookup("foo");
		assertNotNull(h);
		assertEquals(29418
		h = osc.lookup("bar");
		assertNotNull(h);
		assertEquals(29418
		h = osc.lookup("baz");
		assertNotNull(h);
		assertEquals(29418
		h = osc.lookup("\tbar");
		assertNotNull(h);
		assertEquals(22
	}
