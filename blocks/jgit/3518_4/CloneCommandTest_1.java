	public void testCloneRepositoryWithBranch() throws IOException {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals(
				"refs/heads/master
				allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));

		directory = createTempDirectory("testCloneRepositoryWithBranch_bare");
		command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setNoCheckout(true);
		git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master
				allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));

		directory = createTempDirectory("testCloneRepositoryWithBranch_bare");
		command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setBare(true);
		git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/heads/master
				.branchList().setListMode(ListMode.ALL).call()));
