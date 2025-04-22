	@Test
	public void testSwitchToMenu() throws Exception {
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		SWTBotTreeItem node = TestUtil.navigateTo(projectExplorerTree,
				childFolder.getFullPath().segments());
		TestUtil.waitForDecorations();
		node.select();
		ContextMenuHelper.clickContextMenu(projectExplorerTree, "Team",
				util.getPluginLocalizedValue("SwitchToMenu.label"),
				UIText.SwitchToMenu_NewBranchMenuLabel);

		SWTBotShell shell = bot.shell(UIText.CreateBranchWizard_NewBranchTitle);
		shell.close();
	}

