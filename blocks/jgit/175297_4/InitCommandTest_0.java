	@Test
	public void testInitRepositoryCustomDefaultBranch()
			throws Exception {
		File directory = createTempDirectory("testInitRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		MockSystemReader reader = (MockSystemReader) SystemReader.getInstance();
		StoredConfig c = reader.getUserConfig();
		String old = c.getString(ConfigConstants.CONFIG_INIT_SECTION
				ConfigConstants.CONFIG_KEY_DEFAULT_BRANCH);
		c.setString(ConfigConstants.CONFIG_INIT_SECTION
				ConfigConstants.CONFIG_KEY_DEFAULT_BRANCH
		try (Git git = command.call()) {
			Repository r = git.getRepository();
			assertNotNull(r);
			assertEquals("refs/heads/main"
		} finally {
			c.setString(ConfigConstants.CONFIG_INIT_SECTION
					ConfigConstants.CONFIG_KEY_DEFAULT_BRANCH
		}
	}

	@Test
	public void testInitRepositoryNullInitialBranch() throws Exception {
		File directory = createTempDirectory("testInitRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		command.setInitialBranch("main");
		command.setInitialBranch(null);
		try (Git git = command.call()) {
			Repository r = git.getRepository();
			assertNotNull(r);
			assertEquals("refs/heads/master"
		}
	}

	@Test
	public void testInitRepositoryEmptyInitialBranch() throws Exception {
		File directory = createTempDirectory("testInitRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		command.setInitialBranch("main");
		command.setInitialBranch("");
		try (Git git = command.call()) {
			Repository r = git.getRepository();
			assertNotNull(r);
			assertEquals("refs/heads/master"
		}
	}

