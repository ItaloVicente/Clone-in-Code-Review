
	@Test
	public void testCloneRepositoryWithMultipleHeadBranches() throws Exception {
		git.checkout().setName(Constants.MASTER).call();
		git.branchCreate().setName("a").call();

		File directory = createTempDirectory("testCloneRepositoryWithMultipleHeadBranches");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER
	}
