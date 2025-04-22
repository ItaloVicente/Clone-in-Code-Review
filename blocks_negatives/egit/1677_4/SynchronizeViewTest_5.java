		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(false);
		workspace.setDescription(description);

		GitImportRepoWizard importWizard = new GitImportRepoWizard();

		importWizard.openWizard();
		String repoName = "egit";
		if (!importWizard.containsRepo(repoName))
			addRepository(importWizard, repoUrl);

		importWizard.selectAndCloneRepository(repoName);
		importWizard.waitForCreate();
		waitForWorkspaceRefresh();
