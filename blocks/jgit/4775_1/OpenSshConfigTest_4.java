	@Test
	public void testHostFromSystemConfigViaGitPrefix() throws Exception {
		systemConfig(new File(gitDir
				+ "\tHostName example.org\n");
		systemConfig = OpenSshConfig.get(fsMock

		Host h = systemConfig.lookup("host");
		assertEquals("example.org"
	}
