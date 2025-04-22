	@Test
	public void testCloneRepositoryAllBranchesTakesPreference()
			throws Exception {
		File directory = createTempDirectory(
				"testCloneRepositoryAllBranchesTakesPreference");
		CloneCommand command = Git.cloneRepository();
		command.setCloneAllBranches(true);
		command.setBranchesToClone(
				Collections.singletonList("refs/heads/test"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertNotNull(
				git2.getRepository().resolve("refs/remotes/origin/master"));
		assertNotNull(git2.getRepository().resolve("refs/remotes/origin/test"));
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
				specs.get(0));
	}

	@Test
	public void testCloneRepositoryAllBranchesIndependent() throws Exception {
		File directory = createTempDirectory(
				"testCloneRepositoryAllBranchesIndependent");
		CloneCommand command = Git.cloneRepository();
		command.setCloneAllBranches(true);
		command.setBranchesToClone(
				Collections.singletonList("refs/heads/test"));
		command.setCloneAllBranches(false);
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertNull(git2.getRepository().resolve("refs/remotes/origin/master"));
		assertNotNull(git2.getRepository().resolve("refs/remotes/origin/test"));
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
		assertEquals(new RefSpec("+refs/heads/test:refs/remotes/origin/test")
				specs.get(0));
	}

