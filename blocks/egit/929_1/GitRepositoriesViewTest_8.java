	@Test
	public void testImportWizard() throws Exception {
		deleteAllProjects();
		assertProject1Existence(false);
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getRootItem(tree, repositoryFile);
		String wizardTitle = NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				repositoryFile.getPath());
		item.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		SWTBotShell shell = bot.shell(wizardTitle);
		TableCollection selected = shell.bot().tree().selection();
		String wizardNode = selected.get(0, 0);
		assertEquals(getWorkdirItem(tree, repositoryFile).getText(), wizardNode);
		waitInUI();
		shell.close();
		getWorkdirItem(tree, repositoryFile).expand()
				.getNode(Constants.DOT_GIT).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		shell = bot.shell(wizardTitle);
		selected = shell.bot().tree().selection();
		wizardNode = selected.get(0, 0);
		assertEquals(Constants.DOT_GIT, wizardNode);
		shell.bot().button(1).click();
		waitInUI();
		assertTrue(shell.bot().tree().getAllItems().length == 0);
		shell.bot().button(2).click();
		shell.bot().tree().getAllItems()[0].getNode("Project1").select();
		shell.bot().button(1).click();
		waitInUI();
		assertTrue(shell.bot().tree().getAllItems().length == 1);
		shell.bot().button(1).click();
		assertTrue(!shell.bot().button(4).isEnabled());
		shell.bot().button(0).click();
		assertTrue(shell.bot().button(4).isEnabled());
		shell.bot().button(4).click();
		waitInUI();
		assertProject1Existence(true);
