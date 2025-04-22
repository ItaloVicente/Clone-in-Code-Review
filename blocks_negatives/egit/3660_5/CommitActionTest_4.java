	private void clickOnCommit() throws Exception {
		SWTBotTree projectExplorerTree = bot.viewById(
				"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		String menuString = util.getPluginLocalizedValue("CommitAction_label");
		ContextMenuHelper.clickContextMenu(projectExplorerTree, "Team",
				menuString);
