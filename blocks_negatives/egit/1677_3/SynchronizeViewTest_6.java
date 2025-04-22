		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(true);
		workspace.setDescription(description);
	}

	private static void addRepository(GitImportRepoWizard importWizard,
			String repoUrl) {
		RepoPropertiesPage propertiesPage = importWizard.openCloneWizard();

		RepoRemoteBranchesPage remoteBranches = propertiesPage
				.nextToRemoteBranches(repoUrl);
		remoteBranches.selectBranches("master");

		remoteBranches.nextToWorkingCopy().waitForCreate();
