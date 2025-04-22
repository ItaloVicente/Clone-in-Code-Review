		assertNotNull(lookupRepository(repositoryFile).resolve("newBranch"));

		SWTBotShell deleteBranchDialog = openDeleteBranchDialog();
		deleteBranchDialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand().getNode("newBranch").select();
		deleteBranchDialog.bot().button(IDialogConstants.OK_LABEL).click();
		
		TestUtil.joinJobs(JobFamilies.CHECKOUT);
		assertNull(lookupRepository(repositoryFile).resolve("newBranch"));		
	}

	private SWTBotShell openCheckoutBranchDialog() {
		SWTBotTree projectExplorerTree = bot.viewById(
				"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("SwitchToMenu.label"),
				UIText.SwitchToMenu_OtherMenuLabel };
		ContextMenuHelper.clickContextMenu(projectExplorerTree, menuPath);
		SWTBotShell dialog = bot.shell(UIText.CheckoutDialog_WindowTitle);
		return dialog;
	}

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
