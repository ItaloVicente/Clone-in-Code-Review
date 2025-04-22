	public void testImportWizardGeneralProject() throws Exception {
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
				UIText.GitSelectWizardPage_ImportExistingButton);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_AutoShareButton);
		TableCollection selected = shell.bot().tree().selection();
		String wizardNode = selected.get(0, 0);
		assertEquals(PROJ2, wizardNode);
		shell.bot().button(1).click();
		waitInUI();
		assertTrue(shell.bot().tree().getAllItems().length == 0);
		shell.bot().button(2).click();
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_ImportAsGeneralButton);
		shell.bot().button(1).click();
		assertEquals(PROJ2, shell.bot().textWithLabel(
				UIText.GitCreateGeneralProjectPage_ProjectNameLabel).getText());
		shell.bot().button(2).click();
		waitInUI();
		assertProjectExistence(PROJ2, true);
