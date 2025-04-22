	@Test
	public void testImportWizardGeneralProjectManualShareCancel()
			throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ2, false);
		SWTBotTree tree = getOrOpenView().bot().tree();
		String wizardTitle = NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				repositoryFile.getPath());
		myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand().getNode(
				PROJ2).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		SWTBotShell shell = bot.shell(wizardTitle);
		shell = bot.shell(wizardTitle);
		bot.radio(UIText.GitSelectWizardPage_ImportAsGeneralButton).click();
		bot.radio(UIText.GitSelectWizardPage_InteractiveShareButton).click();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		assertEquals(PROJ2, shell.bot().textWithLabel(
				UIText.GitCreateGeneralProjectPage_ProjectNameLabel).getText());
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		assertEquals(PROJ2, shell.bot().table().getTableItem(0).getText(0));
		shell.bot().button(IDialogConstants.CANCEL_LABEL).click();
		waitInUI();
		assertProjectExistence(PROJ2, true);
		RepositoryMapping mapping = RepositoryMapping
				.getMapping(ResourcesPlugin.getWorkspace().getRoot()
						.getProject(PROJ2));
		assertNull(mapping);
	}

	@Test
	public void testImportWizardGeneralProjectManualShareOk() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ2, false);
		SWTBotTree tree = getOrOpenView().bot().tree();
		String wizardTitle = NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				repositoryFile.getPath());
		myRepoViewUtil.getWorkdirItem(tree, repositoryFile).expand().getNode(
				PROJ2).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		SWTBotShell shell = bot.shell(wizardTitle);
		shell = bot.shell(wizardTitle);
		bot.radio(UIText.GitSelectWizardPage_ImportAsGeneralButton).click();
		bot.radio(UIText.GitSelectWizardPage_InteractiveShareButton).click();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		assertEquals(PROJ2, shell.bot().textWithLabel(
				UIText.GitCreateGeneralProjectPage_ProjectNameLabel).getText());
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		assertEquals(PROJ2, shell.bot().table().getTableItem(0).getText(0));
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		waitInUI();
		assertProjectExistence(PROJ2, true);
		RepositoryMapping mapping = RepositoryMapping
				.getMapping(ResourcesPlugin.getWorkspace().getRoot()
						.getProject(PROJ2));
		assertNotNull(mapping);
	}

