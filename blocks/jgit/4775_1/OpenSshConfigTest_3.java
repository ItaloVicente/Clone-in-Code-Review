	@Test
	public void testHostFromSystemConfigViaSystemProperty() throws Exception {
		systemConfig(otherDir
		mockSystemReader.setProperty("jgit.ssh.sysconfdir"
				otherDir.getAbsolutePath());
		systemConfig = OpenSshConfig.get(fsMock

		Host h = systemConfig.lookup("host");
		assertEquals("example.org"
	}
