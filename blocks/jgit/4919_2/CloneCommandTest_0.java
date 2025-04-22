
	@Test
	public void testCloneWithAutoSetupRebase() throws Exception {
		File directory = createTempDirectory("testCloneRepository1");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertFalse(git2
				.getRepository()
				.getConfig()
				.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE

		FileBasedConfig userConfig = SystemReader.getInstance().openUserConfig(
				null
		userConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE
				ConfigConstants.CONFIG_KEY_ALWAYS);
		userConfig.save();
		directory = createTempDirectory("testCloneRepository2");
		command = Git.cloneRepository();
		command.setDirectory(directory);
		git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertTrue(git2
				.getRepository()
				.getConfig()
				.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE

		userConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE
				ConfigConstants.CONFIG_KEY_REMOTE);
		userConfig.save();
		directory = createTempDirectory("testCloneRepository2");
		command = Git.cloneRepository();
		command.setDirectory(directory);
		git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertTrue(git2
				.getRepository()
				.getConfig()
				.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE

	}
