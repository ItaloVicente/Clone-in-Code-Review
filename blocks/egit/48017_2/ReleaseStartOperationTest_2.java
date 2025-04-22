	@Test
	public void testReleaseBranchCreatedFromCommit() throws Exception {
		RevCommit initialCommit = testRepository
				.createInitialCommit("testReleaseBranchCreatedFromCommit\n\nfirst commit\n");
		testRepository
				.createInitialCommit("testReleaseBranchCreatedFromCommit\n\nsecond commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		ReleaseStartOperation releaseStartOperation = new ReleaseStartOperation(
				gfRepo, initialCommit.getName(), MY_RELEASE);
		releaseStartOperation.execute(null);

		assertNotNull(releaseStartOperation.getSchedulingRule());

		assertEquals(gfRepo.getConfig().getFullReleaseBranchName(MY_RELEASE),
				repository.getFullBranch());

		assertEquals(initialCommit, gfRepo.findHead());
	}

