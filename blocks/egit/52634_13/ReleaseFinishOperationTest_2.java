	@Test
	public void testReleaseFinish() throws Exception {
		testRepository
				.createInitialCommit("testReleaseFinish\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		InitParameters initParameters = new InitParameters();
		initParameters.setDevelop(DEVELOP);
		initParameters.setMaster(MASTER);
		initParameters.setFeature(FEATURE_PREFIX);
		initParameters.setRelease(RELEASE_PREFIX);
		initParameters.setHotfix(HOTFIX_PREFIX);
		initParameters.setVersionTag(MY_VERSION_TAG);
		new InitOperation(repository, initParameters).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		new ReleaseStartOperation(gfRepo, MY_RELEASE).execute(null);
		addFileAndCommit("foo.txt", "testReleaseFinish\n\nbranch commit 1\n");
		addFileAndCommit("bar.txt", "testReleaseFinish\n\nbranch commit 2\n");
		ReleaseFinishOperation releaseFinishOperation = new ReleaseFinishOperation(gfRepo);
		releaseFinishOperation.execute(null);
		assertEquals(gfRepo.getConfig().getDevelopFull(),
				repository.getFullBranch());

		String branchName = gfRepo.getConfig().getReleaseBranchName(MY_RELEASE);
		RevCommit taggedCommit = gfRepo.findCommitForTag(MY_VERSION_TAG
				+ MY_RELEASE);
		assertEquals(formatMergeCommitMessage(branchName),
				taggedCommit.getFullMessage());

		assertEquals(findBranch(repository, branchName), null);
	}
