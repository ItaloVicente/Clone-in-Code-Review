	private SWTBotShell openCreateBranchDialog() {
		SWTBotTree projectExplorerTree = bot.viewById(
				"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("SwitchToMenu.label"),
				UIText.SwitchToMenu_NewBranchMenuLabel };
		ContextMenuHelper.clickContextMenu(projectExplorerTree, menuPath);
		SWTBotShell dialog = bot.shell(UIText.CreateBranchDialog_WindowTitle);
		return dialog;
	}
