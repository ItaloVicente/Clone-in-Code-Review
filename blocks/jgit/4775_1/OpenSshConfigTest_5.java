	@Test
	public void testHostFromSystemConfig() throws Exception {
		systemConfig("Host host\n" + "\tHostName example.org\n");

		Host h = systemConfig.lookup("host");
		assertEquals("example.org"
	}

	@Test
	public void testHostFromSystemConfigOverriddenByUserConfig()
			throws Exception {
		userConfig("Host host\n" + "\tHostName test.org\n");
		systemConfig("Host host\n" + "\tHostName example.org\n");

		Host h = systemConfig.lookup("host");
		assertEquals("test.org"
	}

	@Test
	public void testHostFromSystemConfigComplementedByUserConfig()
			throws Exception {
		userConfig("Host host\n" + "\tUser someone");
		systemConfig("Host host\n" + "\tHostName example.org");

		Host h = systemConfig.lookup("host");
		assertEquals("example.org"
		assertEquals("someone"
	}

	private static class FS_Mock extends FS {
