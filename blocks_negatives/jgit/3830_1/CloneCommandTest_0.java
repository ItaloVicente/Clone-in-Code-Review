	public void testCloneRepository() {
		try {
			File directory = createTempDirectory("testCloneRepository");
			CloneCommand command = Git.cloneRepository();
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			Git git2 = command.call();
			addRepoToClose(git2.getRepository());
			assertNotNull(git2);
			ObjectId id = git2.getRepository().resolve("tag-for-blob");
			assertNotNull(id);
			assertEquals(git2.getRepository().getFullBranch(),
					"refs/heads/test");
			assertEquals(
					"origin",
					git2.getRepository()
							.getConfig()
							.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
									"test", ConfigConstants.CONFIG_KEY_REMOTE));
			assertEquals(
					"refs/heads/test",
					git2.getRepository()
							.getConfig()
							.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
									"test", ConfigConstants.CONFIG_KEY_MERGE));
			assertEquals(2, git2.branchList().setListMode(ListMode.REMOTE)
					.call().size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
