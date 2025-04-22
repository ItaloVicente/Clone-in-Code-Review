	public void unStageFile(String path) {
		SWTBotTree stagedTree = stagingView.bot().tree(1);

		TestUtil.waitUntilTreeHasNodeContainsText(stagingView.bot(), stagedTree,
				path, 10000);

		TestUtil.getNode(stagedTree.getAllItems(), path).select();

		JobJoiner jobJoiner = JobJoiner.startListening(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE, 30,
				TimeUnit.SECONDS);

		ContextMenuHelper.clickContextMenu(stagedTree,
				UIText.StagingView_UnstageItemMenuLabel);

		jobJoiner.join();
	}

