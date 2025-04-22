
	@Test
	public void testIsOnDevelop() throws Exception {
		Repository repository = repository1.getRepository();
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		InitParameters initParameters = new InitParameters();
		initParameters.setDevelop(DEVELOP);
		initParameters.setMaster(GitFlowDefaults.MASTER);
		initParameters.setFeature(FEATURE_PREFIX);
		initParameters.setRelease(RELEASE_PREFIX);
		initParameters.setHotfix(HOTFIX_PREFIX);
		initParameters.setVersionTag(VERSION_TAG);
		repository1.checkoutBranch(gfRepo.getConfig().getDevelop());

		RevCommit developBranchCommit = repository1.commit("develop branch commit");
		assertTrue(gfRepo.isOnDevelop(developBranchCommit));

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);
		RevCommit featureBranchCommit = repository1.commit("feature branch commit");
		assertFalse(gfRepo.isOnDevelop(featureBranchCommit));

		assertTrue(gfRepo.isOnDevelop(initialCommit));
	}
