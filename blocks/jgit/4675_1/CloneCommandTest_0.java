
	@Test
	public void testCloneRepositoryWithBranches() throws Exception {
		String dirName = "testCloneRepositoryWithBranches";
		File directory = createTempDirectory(dirName);
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		git2.branchCreate().setName("a").call();
		git2.push().setPushAll().call();

		File directory2 = createTempDirectory(dirName + "-copy");
		CloneCommand command2 = Git.cloneRepository();
		command2.setDirectory(directory2);
		Git git3 = command2.call();
		addRepoToClose(git3.getRepository());
		assertNotNull(git3);

		assertEquals(Constants.MASTER
	}
