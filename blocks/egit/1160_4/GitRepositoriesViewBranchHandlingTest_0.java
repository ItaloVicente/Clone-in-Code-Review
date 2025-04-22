
	@Test
	public void testRenameBranch() throws Exception {
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(clonedRepositoryFile);

		SWTBotTree tree = getOrOpenView().bot().tree();

		SWTBotTreeItem item = myRepoViewUtil.getLocalBranchesItem(tree,
				clonedRepositoryFile).expand();

		item.getNode("master").select();
		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("RepoViewRenameBranch.label"));
		refreshAndWait();

		SWTBotShell renameDialog = bot
				.shell(UIText.RepositoriesView_RenameBranchTitle);
		SWTBotText newBranchNameText = renameDialog.bot().text(0);
		assertEquals("master", newBranchNameText.getText());
		newBranchNameText.setText("invalid~name");

		renameDialog.bot().text(
				NLS.bind(UIText.ValidationUtils_InvalidRefNameMessage,
						"refs/heads/invalid~name"));
		assertFalse(renameDialog.bot().button(IDialogConstants.OK_LABEL)
				.isEnabled());
		newBranchNameText.setText("newmaster");
		renameDialog.bot().button(IDialogConstants.OK_LABEL).click();

		refreshAndWait();

		item = myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile)
				.expand();
		assertEquals("newmaster", item.getNode(0).getText());
	}
