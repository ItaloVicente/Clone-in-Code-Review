	public void testCloneRepositoryOnlyOneBranch() throws IOException {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(Collections
				.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master"
				.branchList().setListMode(ListMode.REMOTE).call()));

		directory = createTempDirectory("testCloneRepositoryWithBranch_bare");
		command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(Collections
				.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setBare(true);
		git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/heads/master"
				.setListMode(ListMode.ALL).call()));
