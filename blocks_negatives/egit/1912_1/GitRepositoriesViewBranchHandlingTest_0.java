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
	}

