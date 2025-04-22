	/**
	 * Figure out which repository to use. All selected
	 * resources must map to the same Git repository.
	 *
	 * @param warn Put up a message dialog to warn why a resource was not selected
	 * @return repository for current project, or null
	 */
	protected Repository getRepository(boolean warn) {
		RepositoryMapping mapping = null;
		for (IProject project : getSelectedProjects()) {
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(project);
			if (mapping == null)
				mapping = repositoryMapping;
			if (repositoryMapping == null)
				return null;
			if (mapping.getRepository() != repositoryMapping.getRepository()) {
				if (warn)
					MessageDialog.openError(getShell(),
							UIText.RepositoryAction_multiRepoSelectionTitle,
							UIText.RepositoryAction_multiRepoSelection);
				return null;
			}
		}
		if (mapping == null) {
			if (warn)
				MessageDialog.openError(getShell(),
						UIText.RepositoryAction_errorFindingRepoTitle,
						UIText.RepositoryAction_errorFindingRepo);
			return null;
