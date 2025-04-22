	@Test
	public void testDeleteFileInProject() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		refreshAndWait();

		IProject project1 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1);
		project1.refreshLocal(IResource.DEPTH_INFINITE, null);

		SWTBotTreeItem folder = findWorkdirNode(tree, PROJ1, FOLDER);
		folder.getNode(FILE1).select();

		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("RepoViewDeleteFile.label"));

		SWTBotShell confirm = bot.shell("Delete Resources");
		confirm.bot().button(IDialogConstants.OK_LABEL).click();
		bot.waitUntil(shellCloses(confirm));
		TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);

		assertThat(folder.getNodes(), not(hasItem(FILE1)));
		assertThat(folder.getNodes(), hasItem(FILE2));
	}

	@Test
	public void testDeleteFileNotInProject() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		refreshAndWait();

		SWTBotTreeItem folder = findWorkdirNode(tree, PROJ2, FOLDER);
		folder.getNode(FILE1).select();

		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("RepoViewDeleteFile.label"));

		SWTBotShell confirm = bot.shell(UIText.DeleteResourcesOperationUI_confirmActionTitle);
		confirm.bot().button(IDialogConstants.OK_LABEL).click();
		bot.waitUntil(shellCloses(confirm));
		TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);

		folder = findWorkdirNode(tree, PROJ2, FOLDER);
		assertThat(folder.getNodes(), not(hasItem(FILE1)));
		assertThat(folder.getNodes(), hasItem(FILE2));
	}

	private SWTBotTreeItem findWorkdirNode(SWTBotTree tree, String... nodes) throws Exception {
		SWTBotTreeItem item = myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand();
		for (String node : nodes)
			item = item.getNode(node).expand();
		return item;
	}
