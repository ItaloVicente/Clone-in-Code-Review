	public void stageFile(String path) {
		SWTBotTree unstagedTree = stagingView.bot().tree(0);
		TestUtil.getNode(unstagedTree.getAllItems(), path).select();

		JobJoiner jobJoiner = JobJoiner.startListening(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE, 30,
				TimeUnit.SECONDS);
		ContextMenuHelper.clickContextMenu(unstagedTree,
				UIText.StagingView_StageItemMenuLabel);
		jobJoiner.join();
	}

