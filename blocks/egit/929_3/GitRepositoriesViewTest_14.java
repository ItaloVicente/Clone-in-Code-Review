	@Test
	public void testImportWizardGeneralProjectManualShareCancel()
			throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ2, false);
		SWTBotTree tree = getOrOpenView().bot().tree();
		String wizardTitle = NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				repositoryFile.getPath());
		getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ2).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		SWTBotShell shell = bot.shell(wizardTitle);
		shell = bot.shell(wizardTitle);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_ImportAsGeneralButton);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_InteractiveShareButton);
		shell.bot().button(1).click();
		assertEquals(PROJ2, shell.bot().textWithLabel(
				UIText.GitCreateGeneralProjectPage_ProjectNameLabel).getText());
		shell.bot().button(1).click();
		assertEquals(PROJ2, shell.bot().table().getTableItem(0).getText(0));
		shell.bot().button(3).click();
		waitInUI();
		assertProjectExistence(PROJ2, true);
		RepositoryMapping mapping = RepositoryMapping
				.getMapping(ResourcesPlugin.getWorkspace().getRoot()
						.getProject(PROJ2));
		assertNull(mapping);
	}
