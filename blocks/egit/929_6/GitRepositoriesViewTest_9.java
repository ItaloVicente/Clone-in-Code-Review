	@Ignore
	public void testContextMenuRepository() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getRootItem(tree, repositoryFile);
		item.select();
		assertClickOpens(tree, myUtil.getPluginLocalizedValue("FetchCommand"),
				UIText.FetchWizard_windowTitleDefault);
		assertClickOpens(tree, myUtil.getPluginLocalizedValue("PushCommand"),
				UIText.PushWizard_windowTitleDefault);
