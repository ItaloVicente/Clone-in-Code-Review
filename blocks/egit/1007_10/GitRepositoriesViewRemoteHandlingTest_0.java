		removeRemotesConfig(repositoryFile);
		refreshAndWait();
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem remotesItem = myRepoViewUtil.getRemotesItem(tree,
				repositoryFile).expand();

		remotesItem = myRepoViewUtil.getRemotesItem(tree, repositoryFile)
				.expand();
		remotesItem.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("NewRemoteCommand"));
		SWTBotShell shell = bot
				.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().textWithLabel(UIText.SelectRemoteNamePage_RemoteNameLabel)
				.setText("testRemote");
		shell.bot().checkBox(UIText.SelectRemoteNamePage_ConfigureFetch_button)
				.select();
		shell.bot().checkBox(UIText.SelectRemoteNamePage_ConfigurePush_button)
				.select();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		shell.bot().button(UIText.ConfigureUriPage_Change_button).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		shell = bot.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		shell.bot().button(UIText.RefSpecPanel_predefinedAll).click();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		String testString = new org.eclipse.jgit.transport.URIish("file:///"
				+ remoteRepositoryFile.getPath()).toPrivateString();
		assertEquals(testString, shell.bot().text().getText());
		shell.bot().button(UIText.ConfigureUriPage_Add_button).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		shell = bot.shell(UIText.ConfigureUriPage_DuplicateUriTitle);
		shell.close();
		shell = bot.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		shell.bot().button(UIText.RefSpecPanel_predefinedAll).click();
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		SWTBotTreeItem item = myRepoViewUtil.getRemotesItem(tree,
				repositoryFile).expand().getNode("testRemote").expand();
		List<String> children = item.getNodes();
		assertTrue(children.size() == 2);
		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveFetchCommand"));
		refreshAndWait();
		item = myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);
		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemovePushCommand"));
		refreshAndWait();
		item = myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 0);

		myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").select();
		String shellText = NLS.bind(
				UIText.ConfigureRemoteWizard_WizardTitle_Change, "testRemote");

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ConfigureFetchCommand"));
		shell = bot.shell(shellText);
		shell.bot().button(UIText.ConfigureUriPage_Change_button).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		shell = bot.shell(shellText);
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		shell.bot().button(UIText.RefSpecPanel_predefinedAll).click();
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		item = myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);

		item = myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);
		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveFetchCommand"));
		refreshAndWait();

		myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand().getNode(
				"testRemote").select();

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ConfigurePushCommand"));
		shell = bot.shell(shellText);
		shell.bot().button(UIText.ConfigureUriPage_Add_button).click();
		shell = bot.shell(UIText.SelectUriWiazrd_Title);
		shell.bot().text().setText("file:///" + remoteRepositoryFile.getPath());
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		shell = bot.shell(shellText);
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		shell.bot().button(UIText.RefSpecPanel_predefinedAll).click();
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		item = myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);
		item.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveRemoteCommand"));
		shell = bot.shell(UIText.RepositoriesView_ConfirmDeleteRemoteHeader);
		shell.bot().button(IDialogConstants.CANCEL_LABEL).click();

		refreshAndWait();
		item = myRepoViewUtil.getRemotesItem(tree, repositoryFile).expand()
				.getNode("testRemote").expand();
		children = item.getNodes();
		assertTrue(children.size() == 1);

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("RemoveRemoteCommand"));
		bot.shell(UIText.RepositoriesView_ConfirmDeleteRemoteHeader).bot()
				.button(IDialogConstants.OK_LABEL).click();
		refreshAndWait();
		assertTrue(myRepoViewUtil.getRemotesItem(tree, repositoryFile)
				.getNodes().isEmpty());
