
	@Test
	public void testButtonEnablement() throws Exception {
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.assertCommitEnabled(false);

		setContent("I have changed this");
		stagingViewTester.assertCommitEnabled(false);

		stagingViewTester.stageFile(FILE1_PATH);
		stagingViewTester.assertCommitEnabled(true);

		stagingViewTester.unStageFile(FILE1_PATH);
		stagingViewTester.assertCommitEnabled(false);

		stagingViewTester.setAmend(true);
		stagingViewTester.assertCommitEnabled(true);

		stagingViewTester.setAmend(false);
		stagingViewTester.assertCommitEnabled(false);
	}
