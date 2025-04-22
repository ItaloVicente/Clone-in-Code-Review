		String menuString = util.getPluginLocalizedValue("BranchAction_label");
		ContextMenuHelper.clickContextMenu(projectExplorerTree, "Team",
				menuString);
		SWTBotShell dialog = bot.shell(NLS.bind(
				UIText.BranchSelectionDialog_TitleCheckout, repositoryFile
						.toString()));
