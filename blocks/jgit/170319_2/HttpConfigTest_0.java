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

