
	@Test
	public void testDeleteSingleBranch() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		refreshAndWait();
		SWTBotTreeItem localBranchesItem = getLocalBranchesItem(tree,
				repositoryFile).expand();
		SWTBotTreeItem masterNode = localBranchesItem.getNode("master");
		masterNode.select();
		ContextMenuHelper.clickContextMenu(tree, "Create Branch...");
		SWTBotShell createBranchShell = bot.shell("Create Branch");
		createBranchShell.bot().text("").setText("abc");
		createBranchShell.bot().checkBox().deselect();
		createBranchShell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		localBranchesItem.getNode("abc").select();
		ContextMenuHelper.clickContextMenu(tree, "Delete Branch...");

		SWTBotShell deleteBranchDialog = bot.shell(UIText.RepositoriesView_ConfirmDeleteTitle);
		deleteBranchDialog.bot().button(IDialogConstants.OK_LABEL).click();
		refreshAndWait();
		SWTBotTreeItem[] items = getLocalBranchesItem(tree, repositoryFile)
				.getItems();
		assertEquals("Wrong number of branches", 2, items.length);
		assertEquals("master", items[0].getText());
		assertEquals("stable", items[1].getText());
	}

	@Test
	public void testDeleteMultipleBranches() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		refreshAndWait();
		SWTBotTreeItem localBranchesItem = getLocalBranchesItem(tree,
				repositoryFile).expand();
		SWTBotTreeItem masterNode = localBranchesItem.getNode("master");
		masterNode.select();
		ContextMenuHelper.clickContextMenu(tree, "Create Branch...");
		SWTBotShell createBranchShell = bot.shell("Create Branch");
		createBranchShell.bot().text("").setText("abc");
		createBranchShell.bot().checkBox().deselect();
		createBranchShell.bot().button(IDialogConstants.FINISH_LABEL).click();
		ContextMenuHelper.clickContextMenu(tree, "Create Branch...");
		createBranchShell = bot.shell("Create Branch");
		createBranchShell.bot().text("").setText("123");
		createBranchShell.bot().checkBox().deselect();
		createBranchShell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		localBranchesItem = getLocalBranchesItem(tree,
				repositoryFile).expand();
		localBranchesItem.select("abc", "123");
		ContextMenuHelper.clickContextMenu(tree, UIText.RepositoriesView_DeleteBranchMenu);

		SWTBotShell deleteBranchDialog = bot.shell(UIText.RepositoriesView_ConfirmDeleteTitle);
		assertNotNull(deleteBranchDialog.bot().table(0).getTableItem("refs/heads/abc"));
		assertNotNull(deleteBranchDialog.bot().table(0).getTableItem("refs/heads/123"));
		deleteBranchDialog.bot().button(IDialogConstants.OK_LABEL).click();
		refreshAndWait();

		SWTBotTreeItem[] items = getLocalBranchesItem(tree, repositoryFile)
				.getItems();
		assertEquals("Wrong number of branches", 2, items.length);
		assertEquals("master", items[0].getText());
		assertEquals("stable", items[1].getText());
	}

