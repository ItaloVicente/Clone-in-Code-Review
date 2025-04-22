	public void testCloneRepository() throws IOException {
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		ObjectId id = git2.getRepository().resolve("tag-for-blob");
		assertNotNull(id);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals(
				"origin"
				git2.getRepository()
						.getConfig()
						.getString(ConfigConstants.CONFIG_BRANCH_SECTION
								"test"
		assertEquals(
				"refs/heads/test"
				git2.getRepository()
						.getConfig()
						.getString(ConfigConstants.CONFIG_BRANCH_SECTION
								"test"
		assertEquals(2
				.size());
