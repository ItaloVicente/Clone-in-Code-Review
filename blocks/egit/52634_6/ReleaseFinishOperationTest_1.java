	@Test
	public void testReleaseFinish() throws Exception {
		testRepository
				.createInitialCommit("testReleaseFinish\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository, DEVELOP, MASTER, FEATURE_PREFIX,
				RELEASE_PREFIX, HOTFIX_PREFIX, MY_VERSION_TAG).execute(null);
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
