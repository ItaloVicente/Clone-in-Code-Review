	@Test
	public void testCommitWithoutAutoStage() throws Exception {
		setTestFileContent("I have changed this");
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		util.getProjectItems(projectExplorerTree, PROJ1)[0].select();
		String menuString = util.getPluginLocalizedValue("CommitAction_label");
		ContextMenuHelper.clickContextMenu(projectExplorerTree, "Team",
				menuString);
		TestUtil.joinJobs(ADD_TO_INDEX);
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		TestUtil.waitUntilViewWithGivenIdShows(StagingView.VIEW_ID);
		StagingUtil.assertStaging(PROJ1, FOLDER + '/' + FILE1, false);
	}

	@Test
	public void testCommitWithAutoStageNoUntracked() throws Exception {
		assertCommitWithAutoStage(false);
	}

	@Test
	public void testCommitWithAutoStageWithUntracked() throws Exception {
		assertCommitWithAutoStage(true);
	}

	private void assertCommitWithAutoStage(boolean withUntracked)
			throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.AUTO_STAGE_ON_COMMIT, true);
		boolean initialIncludeUntracked = Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED);
		Activator.getDefault().getPreferenceStore().setValue(
				UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED, withUntracked);
		String newFile = "newFile.txt";
		try {
			setTestFileContent("I have changed this");
			touch(PROJ1, newFile, "New file content");
			SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
			util.getProjectItems(projectExplorerTree, PROJ1)[0].select();
			String menuString = util
					.getPluginLocalizedValue("CommitAction_label");
			JobJoiner joiner = JobJoiner.startListening(ADD_TO_INDEX, 10,
					TimeUnit.SECONDS);
			ContextMenuHelper.clickContextMenu(projectExplorerTree, "Team",
					menuString);
			joiner.join();
			TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
			TestUtil.waitUntilViewWithGivenIdShows(StagingView.VIEW_ID);
			StagingUtil.assertStaging(PROJ1, FOLDER + '/' + FILE1, true);
			StagingUtil.assertStaging(PROJ1, newFile, withUntracked);
		} finally {
			Activator.getDefault().getPreferenceStore().setValue(
					UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED,
					initialIncludeUntracked);
		}
	}

