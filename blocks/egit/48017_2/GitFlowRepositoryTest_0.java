	@Test(expected = WrongGitFlowStateException.class)
	public void testFindHeadFailOnEmptyRepository() throws Exception {
		File workdir3 = testUtils.createTempDir("Repository3");
		TestRepository repository3 = new TestRepository(new File(workdir3, DOT_GIT));
		GitFlowRepository gfRepo = new GitFlowRepository(repository3.getRepository());

		gfRepo.findHead();
	}

