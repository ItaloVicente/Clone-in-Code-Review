
	@Test
	public void testDeleteSubmoduleRepository() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		shareProjects(repositoryFile);
		assertProjectExistence(PROJ1, true);
		refreshAndWait();
		assertHasRepo(repositoryFile);

		Repository db = lookupRepository(repositoryFile);
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository subRepo = command.call();
		assertNotNull(subRepo);

		refreshAndWait();

		SWTBotTree tree = getOrOpenView().bot().tree();
		tree.getAllItems()[0]
				.expand()
				.expandNode(
						UIText.RepositoriesViewLabelProvider_SubmodulesNodeText)
				.getItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue(DELETE_REPOSITORY_CONTEXT_MENU_LABEL));
		SWTBotShell shell = bot
				.shell(UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryWindowTitle);
		shell.activate();
		String workDir = subRepo.getWorkTree().getPath();
		String checkboxLabel = NLS
				.bind(UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox,
						workDir);
		shell.bot().checkBox(checkboxLabel).select();
		shell.bot().button(IDialogConstants.OK_LABEL).click();
		waitInUI();
		refreshAndWait();
		assertFalse(subRepo.getDirectory().exists());
		assertFalse(subRepo.getWorkTree().exists());
	}
