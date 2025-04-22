	}

	@Test
	public void testInitMissingMaster() throws Exception {
		init(MASTER_BRANCH_MISSING);

		bot.waitUntil(shellIsActive(UIText.InitDialog_masterBranchIsMissing));
		bot.button("Yes").click();
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY,
				"Git flow jobs"));

		GitFlowRepository gitFlowRepository = new GitFlowRepository(repository);
		GitFlowConfig config = gitFlowRepository.getConfig();

		assertEquals(DEVELOP_BRANCH, repository.getBranch());
		assertEquals(MASTER_BRANCH_MISSING, config.getMaster());
		assertEquals(FEATURE_BRANCH_PREFIX, config.getFeaturePrefix());
		assertEquals(RELEASE_BRANCH_PREFIX, config.getReleasePrefix());
		assertEquals(HOTFIX_BRANCH_PREFIX, config.getHotfixPrefix());
		assertEquals(VERSION_TAG_PREFIX, config.getVersionTagPrefix());
