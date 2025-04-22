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
		shell.bot().tree().getAllItems()[0].getNode(PROJ1).select();
		shell.bot().button(1).click();
		waitInUI();
		assertTrue(shell.bot().tree().getAllItems().length == 1);
		shell.bot().button(1).click();
		assertTrue(!shell.bot().button(4).isEnabled());
		shell.bot().button(0).click();
		assertTrue(shell.bot().button(4).isEnabled());
		shell.bot().button(4).click();
		waitInUI();
		assertProjectExistence(PROJ1, true);
