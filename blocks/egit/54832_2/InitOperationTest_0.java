
	@Test(expected = CoreException.class)
	public void testInitLocalMasterMissing() throws Exception {
		testRepository
				.createInitialCommit("testInitLocalMasterMissing\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new RenameBranchOperation(repository, repository.getRef(repository
				.getBranch()), "foobar").execute(null);

		new InitOperation(repository).execute(null);
	}
