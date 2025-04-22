	@Test
	public void testFeatureFinish() throws Exception {
		String fileName = "theFirstFile.txt";

		Repository repository = testRepository.getRepository();
		GitFlowRepository gfRepo = init("testFeatureFinish\n\nfirst commit\n");

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);
		RevCommit branchCommit = addFileAndCommit(fileName, "adding file on feature branch");
		new FeatureFinishOperation(gfRepo).execute(null);
		assertEquals(gfRepo.getConfig().getDevelopFull(), repository.getFullBranch());

		String branchName = gfRepo.getConfig().getFeatureBranchName(MY_FEATURE);
		assertEquals(null, findBranch(repository, branchName));

		RevCommit developHead = gfRepo.findHead();
		assertEquals(branchCommit, developHead);

		assertEquals(2, countCommits(repository));
		assertTrue(new File(repository.getDirectory() + "/../" + fileName).exists());
	}

