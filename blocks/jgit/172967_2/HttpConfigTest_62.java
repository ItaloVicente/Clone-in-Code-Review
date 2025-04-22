
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
	public void testUserAgentEnvOverride() throws Exception {
		String mockAgent = "jgit-test/5.10.0";
		SystemReader originalReader = SystemReader.getInstance();
		SystemReader.setInstance(new MockSystemReader() {

			@Override
			public String getenv(String variable) {
				if ("GIT_HTTP_USER_AGENT".equals(variable)) {
					return mockAgent;
				}
				return super.getenv(variable);
			}
		});
		try {
					+ "\tuserAgent=DummyAgent/4.0\n");
			HttpConfig http = new HttpConfig(config
			assertEquals(mockAgent
		} finally {
			SystemReader.setInstance(originalReader);
		}
	}

	@Test
	public void testUserAgentNonAscii() throws Exception {
				+ "\tuserAgent= d ümmy Agent -5.10\n");
		HttpConfig http = new HttpConfig(config
		assertEquals("d.mmy.Agent.-5.10"
	}
