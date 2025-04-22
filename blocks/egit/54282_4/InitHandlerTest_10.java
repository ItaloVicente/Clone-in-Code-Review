		GitFlowRepository gitFlowRepository = new GitFlowRepository(repository);
		GitFlowConfig config = gitFlowRepository.getConfig();

		assertEquals(DEVELOP_BRANCH, repository.getBranch());
		assertEquals(MASTER_BRANCH, config.getMaster());
		assertEquals(FEATURE_BRANCH_PREFIX, config.getFeaturePrefix());
		assertEquals(RELEASE_BRANCH_PREFIX, config.getReleasePrefix());
		assertEquals(HOTFIX_BRANCH_PREFIX, config.getHotfixPrefix());
		assertEquals(VERSION_TAG_PREFIX, config.getVersionTagPrefix());

