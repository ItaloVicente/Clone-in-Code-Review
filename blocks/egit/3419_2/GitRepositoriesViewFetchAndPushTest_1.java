
	private void fetchFromOrigin(SWTBotTree tree) throws Exception,
			InterruptedException {
		myRepoViewUtil.getRemotesItem(tree, clonedRepositoryFile).expand().getNode(
				"origin").expand().getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("SimpleFetchCommand"));
		TestUtil.joinJobs(JobFamilies.FETCH);
	}
