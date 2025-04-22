		ReleaseStartOperation releaseStartOperation = new ReleaseStartOperation(
				gfRepo, MY_RELEASE);
		releaseStartOperation.execute(null);

		assertNull(releaseStartOperation.getSchedulingRule());

		assertEquals(gfRepo.getConfig().getFullReleaseBranchName(MY_RELEASE),
				repository.getFullBranch());
	}

	@Test
	public void testReleaseBranchCreatedFromHeadCommit() throws Exception {
		RevCommit initialCommit = testRepository
				.createInitialCommit("testReleaseBranchCreatedFromHeadCommit\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		ReleaseStartOperation releaseStartOperation = new ReleaseStartOperation(
				gfRepo, initialCommit.getName(), MY_RELEASE);
		releaseStartOperation.execute(null);

		assertNull(releaseStartOperation.getSchedulingRule());
