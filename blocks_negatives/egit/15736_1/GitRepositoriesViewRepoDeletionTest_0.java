		Repository repo = lookupRepository(repositoryFile);
		String workDir=repo.getWorkTree().getPath();
		String checkboxLabel = NLS
				.bind(UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox,
						workDir);
		shell.bot().checkBox(checkboxLabel).select();
