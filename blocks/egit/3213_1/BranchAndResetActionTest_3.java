		String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("AdvancedMenu.label"),
				util.getPluginLocalizedValue("RenameBranchMenu.label") };
		ContextMenuHelper.clickContextMenu(projectExplorerTree, menuPath);
		SWTBotShell dialog = bot.shell(UIText.RenameBranchDialog_WindowTitle);
		return dialog;
	}

	private SWTBotShell openDeleteBranchDialog() {
		SWTBotTree projectExplorerTree = bot.viewById(
				"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("AdvancedMenu.label"),
				util.getPluginLocalizedValue("DeleteBranchMenu.label") };
		ContextMenuHelper.clickContextMenu(projectExplorerTree, menuPath);
		SWTBotShell dialog = bot.shell(UIText.DeleteBranchDialog_WindowTitle);
