
	@Test
	public void testExtraHeaders() throws Exception {
				+ "\textraHeader=foo: bar\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1
		assertEquals("foo: bar"
	}

	@Test
	public void testUserAgent() throws Exception {
				+ "\tuserAgent=DummyAgent/4.0\n");
		HttpConfig http = new HttpConfig(config
		assertEquals("DummyAgent/4.0"
	}
