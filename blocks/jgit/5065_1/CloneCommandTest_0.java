
	@Test
	public void testCloneShouldSetUpRebaseIfAutoSetupRebaseSetToRemote()
			throws Exception {
		FileBasedConfig userConfig = getUserConfig();
		userConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE
				ConfigConstants.CONFIG_KEY_REMOTE);

		assertRebaseIsConfiguredAfterClone();
	}

	@Test
	public void testCloneShouldSetUpRebaseIfAutoSetupRebaseSetToAlways()
			throws Exception {
		FileBasedConfig userConfig = getUserConfig();
		userConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE
				ConfigConstants.CONFIG_KEY_ALWAYS);

		assertRebaseIsConfiguredAfterClone();
	}

	private FileBasedConfig getUserConfig() {
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
		FileBasedConfig userConfig = SystemReader.getInstance().openUserConfig(
				null
		return userConfig;
	}

	private void assertRebaseIsConfiguredAfterClone() throws Exception {
		File directory = createTempDirectory("testCloneSetupRebase");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		Git git2 = clone.call();
		Repository clonedRepo = git2.getRepository();
		addRepoToClose(git2.getRepository());

		boolean rebase = clonedRepo.getConfig().getBoolean(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		assertTrue("rebase=true should have been automatically configured"
				rebase);
	}
