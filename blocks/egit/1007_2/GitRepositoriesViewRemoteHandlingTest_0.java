		removeRemotesConfig(repositoryFile);
		refreshAndWait();
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem remotesItem = getRemotesItem(tree, repositoryFile)
				.expand();

		remotesItem = getRemotesItem(tree, repositoryFile).expand();
		remotesItem.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("NewRemoteCommand"));
		SWTBotShell shell = bot
				.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().textWithLabel(UIText.SelectRemoteNamePage_RemoteNameLabel)
				.setText("testRemote");
		shell.bot().checkBox(0).select();
		shell.bot().checkBox(1).select();
		shell.bot().button(1).click();
		shell.bot().button(0).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(1).click();
		shell = bot.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().button(2).click();
		shell.bot().button(2).click();
		shell.bot().button(7).click();
		String testString = new org.eclipse.jgit.transport.URIish("file:///"
				+ remoteRepositoryFile.getPath()).toPrivateString();
		assertEquals(testString, shell.bot().text().getText());
		shell.bot().toolbarButton(0).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(1).click();
		shell = bot.shell(UIText.ConfigureUriPage_DuplicateUriTitle);
		shell.close();
		shell = bot.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().button(1).click();
		shell.bot().button(3).click();
		shell.bot().button(9).click();
		refreshAndWait();
		SWTBotTreeItem item = getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		List<String> children = item.getNodes();
		assertTrue(children.size() == 2);
		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveFetchCommand"));
		refreshAndWait();
		item = getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);
		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemovePushCommand"));
		refreshAndWait();
		item = getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 0);

		getRemotesItem(tree, repositoryFile).expand().getNode("testRemote")
				.select();
		String shellText = NLS.bind(
				UIText.ConfigureRemoteWizard_WizardTitle_Change, "testRemote");

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ConfigureFetchCommand"));
		shell = bot.shell(shellText);
		shell.bot().button(0).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(1).click();
		shell = bot.shell(shellText);
		shell.bot().button(2).click();
		shell.bot().button(2).click();
		shell.bot().button(8).click();
		refreshAndWait();
		item = getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ConfigurePushCommand"));
		shell = bot.shell(shellText);

		shell.bot().toolbarButton(0).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(1).click();
		shell = bot.shell(shellText);
		shell.bot().button(1).click();
		shell.bot().button(3).click();
		shell.bot().button(9).click();
		refreshAndWait();
		item = getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 2);
		item.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveRemoteCommand"));
		shell = bot.shell(UIText.RepositoriesView_ConfirmDeleteRemoteHeader);
		shell.bot().button(1).click();

		refreshAndWait();
		item = getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 2);

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveRemoteCommand"));
		bot.shell(UIText.RepositoriesView_ConfirmDeleteRemoteHeader).bot()
				.button(0).click();
		refreshAndWait();
		assertTrue(getRemotesItem(tree, repositoryFile).getNodes().isEmpty());
