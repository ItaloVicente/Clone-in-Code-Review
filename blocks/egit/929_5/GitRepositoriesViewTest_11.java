	public void testImportWizard() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getRootItem(tree, repositoryFile);
		String wizardTitle = NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				repositoryFile.getPath());
		item.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		SWTBotShell shell = bot.shell(wizardTitle);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_ImportExistingButton);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_AutoShareButton);
		TableCollection selected = shell.bot().tree().selection();
		String wizardNode = selected.get(0, 0);
		assertEquals(getWorkdirItem(tree, repositoryFile).getText(), wizardNode);
		waitInUI();
