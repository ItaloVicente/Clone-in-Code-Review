
	@Test
	public void testExtraHeaders() throws Exception {
				+ "\textraHeader=foo: bar\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1
		assertEquals("foo: bar"
	}

	@Test
	public void testExtraHeadersMultiple() throws Exception {
				+ "\textraHeader=bar: foo\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(2
		assertEquals("foo: bar"
		assertEquals("bar: foo"
	}

	@Test
	public void testExtraHeadersReset() throws Exception {
				+ "\textraHeader=\n");
		HttpConfig http = new HttpConfig(config
		assertTrue(http.getExtraHeaders().isEmpty());
	}

	@Test
	public void testExtraHeadersResetAndMore() throws Exception {
				+ "\textraHeader=baz: something\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1
		assertEquals("baz: something"
	}

	@Test
	public void testUserAgent() throws Exception {
				+ "\tuserAgent=DummyAgent/4.0\n");
		HttpConfig http = new HttpConfig(config
		assertEquals("DummyAgent/4.0"
	}

	@Test
	public void testUserAgentNonAscii() throws Exception {
				+ "\tuserAgent= d ümmy Agent -5.10\n");
		HttpConfig http = new HttpConfig(config
		assertEquals("d.mmy.Agent.-5.10"
	}
