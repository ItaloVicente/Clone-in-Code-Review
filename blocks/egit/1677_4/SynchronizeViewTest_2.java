		new Eclipse().openPreferencePage(null);
		bot.tree().getTreeItem("Team").expand().select();
		SWTBotRadio syncPerspectiveCheck = bot.radio("Never");
		if (!syncPerspectiveCheck.isSelected())
			syncPerspectiveCheck.click();

		bot.button("OK").click();

		File repositoryFile = createProjectAndCommitToRepository();
		createChildRepository(repositoryFile);
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
