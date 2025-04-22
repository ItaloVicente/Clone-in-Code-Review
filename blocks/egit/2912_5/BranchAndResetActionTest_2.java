		String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("AdvancedMenu.label"),
				util.getPluginLocalizedValue("RenameBranchMenu.label") };
		ContextMenuHelper.clickContextMenu(projectExplorerTree, menuPath);
		SWTBotShell dialog = bot.shell(UIText.RenameBranchDialog_WindowTitle);
