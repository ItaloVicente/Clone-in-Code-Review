		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		ObjectId id = git2.getRepository().resolve("tag-for-blob");
		assertNotNull(id);
		assertEquals(git2.getRepository().getFullBranch(), "refs/heads/test");
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
		assertEquals(2, git2.branchList().setListMode(ListMode.REMOTE).call()
				.size());
		assertEquals(new RefSpec("+refs/heads/*:refs/remotes/origin/*"),
				fetchRefSpec(git2.getRepository()));
