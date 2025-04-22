		assertEquals("newmaster", item.getNode(0).select().getText());

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RepoViewRenameBranch.label"));
		refreshAndWait();

		renameDialog = bot.shell(UIText.RepositoriesView_RenameBranchTitle);
		newBranchNameText = renameDialog.bot().text(0);
		assertEquals("newmaster", newBranchNameText.getText());

		newBranchNameText.setText("master");
		renameDialog.bot().button(IDialogConstants.OK_LABEL).click();

		refreshAndWait();

		item = myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile)
				.expand();
		assertEquals("master", item.getNode(0).select().getText());
	}
	
	@Test
	public void testMergeOnRepo() throws Exception {
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				clonedRepositoryFile);

		SWTBotTree tree = getOrOpenView().bot().tree();

		myRepoViewUtil.getRootItem(tree, clonedRepositoryFile).select();

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RepoViewMerge.label"));

		String title = NLS.bind(UIText.MergeTargetSelectionDialog_TitleMerge,
				clonedRepositoryFile.getPath().toString());

		SWTBotShell mergeDialog = bot.shell(title);
		mergeDialog.close();
