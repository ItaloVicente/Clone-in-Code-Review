	public static void setup() throws Exception {
		repositoryFile = createProjectAndCommitToRepository();
		repository = lookupRepository(repositoryFile);
		TestUtil.configureTestCommitterAsUser(repository);
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		repositoriesView = repoViewUtil.openRepositoriesView(bot);
		repoViewTree = repositoriesView.bot().tree();
	}

	@Test
	public void testCommitSingleFile() throws Exception {
		setTestFileContent("I have changed this");
		new Git(repository).add().addFilepattern(".").call();
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		selectRepositoryNode();
		TestUtil.joinJobs(JobFamilies.STAGING_VIEW_REFRESH);
		stagingViewTester.setAuthor(TestUtil.TESTAUTHOR);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("The new commit");
		stagingViewTester.commit();
		TestUtil.checkHeadCommit(repository, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, "The new commit");
