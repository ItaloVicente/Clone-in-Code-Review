	@Test
	public void testFeatureFinish() throws Exception {
		Repository repository = testRepository.getRepository();
		GitFlowRepository gfRepo = init("testFeatureFinish\n\nfirst commit\n");

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);
		addFileAndCommit("foo.txt", "testFeatureFinish\n\nbranch commit 1\n");
		addFileAndCommit("bar.txt", "testFeatureFinish\n\nbranch commit 2\n");
		new FeatureFinishOperation(gfRepo).execute(null);
		assertEquals(gfRepo.getConfig().getDevelopFull(),
				repository.getFullBranch());

		String branchName = gfRepo.getConfig().getFeatureBranchName(MY_FEATURE);

		assertEquals(formatMergeCommitMessage(branchName), gfRepo.findHead()
				.getFullMessage());
	}

