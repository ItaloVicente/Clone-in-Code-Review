		new Eclipse().openPreferencePage(null);
		bot.tree().getTreeItem("Team").expand().select();
		SWTBotRadio syncPerspectiveCheck = bot.radio("Never");
		if (!syncPerspectiveCheck.isSelected())
			syncPerspectiveCheck.click();

		bot.button(IDialogConstants.OK_LABEL).click();

		File repositoryFile = createProjectAndCommitToRepository();
		createChildRepository(repositoryFile);
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
