	@Test
	public void testFeatureFinishKeepBranch() throws Exception {
		Repository repository = testRepository.getRepository();
		GitFlowRepository gfRepo = init("testFeatureFinishKeepBranch\n\nfirst commit\n");

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);
		addFileAndCommit("foo.txt", "testFeatureFinishKeepBranch\n\nbranch commit 1\n");
		addFileAndCommit("bar.txt", "testFeatureFinishKeepBranch\n\nbranch commit 2\n");
		FeatureFinishOperation featureFinishOperation = new FeatureFinishOperation(gfRepo);
		featureFinishOperation.setKeep(true);
		featureFinishOperation.execute(null);
		assertEquals(gfRepo.getConfig().getDevelopFull(),
				repository.getFullBranch());

		String branchName = gfRepo.getConfig().getFeatureBranchName(MY_FEATURE);
		assertNotNull(findBranch(repository, branchName));

		assertEquals(formatMergeCommitMessage(branchName) + " into develop", gfRepo.findHead()
				.getFullMessage());
	}

