
	@Test
	public void testRenameBranch() throws Exception {
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(clonedRepositoryFile);

		SWTBotTree tree = getOrOpenView().bot().tree();

		SWTBotTreeItem item = myRepoViewUtil.getLocalBranchesItem(tree,
				clonedRepositoryFile).expand();

		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("RenameBranchCommand"));
		refreshAndWait();

		SWTBotShell renameDialog = bot
				.shell(UIText.RepositoriesView_RenameBranchTitle);
		SWTBotText newBranchNameText = renameDialog.bot().text(0);
		assertEquals("master", newBranchNameText.getText());
		newBranchNameText.setText("invalid~name");

		renameDialog.bot().text(
				UIText.BranchSelectionDialog_ErrorInvalidRefName);
		assertFalse(renameDialog.bot().button(IDialogConstants.OK_LABEL)
				.isEnabled());
		newBranchNameText.setText("newmaster");
		renameDialog.bot().button(IDialogConstants.OK_LABEL).click();

		refreshAndWait();

		item = myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile)
				.expand();
		assertEquals("newmaster", item.getNode(0).getText());
	}
