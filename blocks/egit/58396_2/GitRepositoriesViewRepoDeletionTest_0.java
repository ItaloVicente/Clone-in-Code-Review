	@Test
	public void testDeleteRepositoryKeepProjectsBug479964() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		shareProjects(repositoryFile);
		assertProjectExistence(PROJ1, true);
		refreshAndWait();
		assertHasRepo(repositoryFile);
		SWTBotTree tree = getOrOpenView().bot().tree();
		tree.getAllItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue(DELETE_REPOSITORY_CONTEXT_MENU_LABEL));
		SWTBotShell shell = bot.shell(
				UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryWindowTitle);
		shell.activate();
		shell.bot()
				.checkBox(
						UIText.DeleteRepositoryConfirmDialog_DeleteGitDirCheckbox)
				.select();
		SWTBotCheckBox checkbox = shell.bot().checkBox(
				UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox);
		checkbox.select();
		checkbox.deselect();
		shell.bot().button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(JobFamilies.REPOSITORY_DELETE);

		refreshAndWait();
		assertEmpty();
		assertProjectExistence(PROJ1, false);
		assertFalse(repositoryFile.exists());
		assertTrue(
				new File(repositoryFile.getParentFile(), PROJ1).isDirectory());
	}

