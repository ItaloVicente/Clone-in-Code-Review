	public void testCloneRepositoryOnlyOneBranch() {
		try {
			File directory = createTempDirectory("testCloneRepositoryWithBranch");
			CloneCommand command = Git.cloneRepository();
			command.setBranch("refs/heads/master");
			command.setBranchesToClone(Collections
					.singletonList("refs/heads/master"));
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			Git git2 = command.call();
			addRepoToClose(git2.getRepository());
			assertNotNull(git2);
			assertEquals(git2.getRepository().getFullBranch(),
					"refs/heads/master");
			assertEquals("refs/remotes/origin/master",
					allRefNames(git2.branchList()
					.setListMode(ListMode.REMOTE).call()));

			directory = createTempDirectory("testCloneRepositoryWithBranch_bare");
			command = Git.cloneRepository();
			command.setBranch("refs/heads/master");
			command.setBranchesToClone(Collections
					.singletonList("refs/heads/master"));
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			command.setBare(true);
			git2 = command.call();
			addRepoToClose(git2.getRepository());
			assertNotNull(git2);
			assertEquals(git2.getRepository().getFullBranch(),
					"refs/heads/master");
			assertEquals("refs/heads/master", allRefNames(git2
					.branchList().setListMode(ListMode.ALL).call()));
		} catch (Exception e) {
			fail(e.getMessage());
		}
