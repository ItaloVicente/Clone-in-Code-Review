
	@Test
	public void testNegativeMatch() throws Exception {
		config("Host foo.bar !foobar.baz *.baz\n" + "Port 29418\n");
		Host h = osc.lookup("foo.bar");
		assertNotNull(h);
		assertEquals(29418
		h = osc.lookup("foobar.baz");
		assertNotNull(h);
		assertEquals(22
		h = osc.lookup("foo.baz");
		assertNotNull(h);
		assertEquals(29418
	}

	@Test
	public void testNegativeMatch2() throws Exception {
		config("Host foo.bar *.baz !foobar.baz\n" + "Port 29418\n");
		Host h = osc.lookup("foo.bar");
		assertNotNull(h);
		assertEquals(29418
		h = osc.lookup("foobar.baz");
		assertNotNull(h);
		assertEquals(22
		h = osc.lookup("foo.baz");
		assertNotNull(h);
		assertEquals(29418
	}

	@Test
	public void testNoMatch() throws Exception {
		config("Host !host1 !host2\n" + "Port 29418\n");
		Host h = osc.lookup("host1");
		assertNotNull(h);
		assertEquals(22
		h = osc.lookup("host2");
		assertNotNull(h);
		assertEquals(22
		h = osc.lookup("host3");
		assertNotNull(h);
		assertEquals(22
	}

	@Test
	public void testMultipleMatch() throws Exception {
		config("Host foo.bar\nPort 29418\nIdentityFile /foo\n\n"
				+ "Host *.bar\nPort 22\nIdentityFile /bar\n"
				+ "Host foo.bar\nPort 47\nIdentityFile /baz\n");
		Host h = osc.lookup("foo.bar");
		assertNotNull(h);
		assertEquals(29418
		assertArrayEquals(new Object[] { "/foo"
				h.getConfig().getValues("IdentityFile"));
	}

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
