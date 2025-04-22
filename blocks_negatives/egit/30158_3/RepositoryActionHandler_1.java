		return getRepository(selection);
	}

	static Repository getRepository(IEvaluationContext evaluationContext) {
		return getRepository(getSelection(evaluationContext));
	}

	private static Repository getRepository(IStructuredSelection selection) {
		return getRepository(false, selection, null);
	}

	/**
	 * Figure out which repository to use. All selected resources must map to
	 * the same Git repository.
	 *
	 * @param warn
	 *            Put up a message dialog to warn why a resource was not
	 *            selected
	 * @param selection
	 * @param shell
	 *            must be provided if warn = true
	 * @return repository for current project, or null
	 */
	private static Repository getRepository(boolean warn,
			IStructuredSelection selection, Shell shell) {
		RepositoryMapping mapping = null;
		for (IPath location : getSelectedLocations(selection)) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(location);
			if (mapping == null)
				mapping = repositoryMapping;
			if (repositoryMapping == null)
				return null;
			if (mapping.getRepository() != repositoryMapping.getRepository()) {
				if (warn)
					MessageDialog.openError(shell,
							UIText.RepositoryAction_multiRepoSelectionTitle,
							UIText.RepositoryAction_multiRepoSelection);
				return null;
			}
		}
		Repository result = null;
		if (mapping == null)
			for (Object o : selection.toArray()) {
				Repository nextRepo = null;
				if (o instanceof Repository)
					nextRepo = (Repository) o;
				else if (o instanceof PlatformObject)
					nextRepo = (Repository) ((PlatformObject) o)
							.getAdapter(Repository.class);
				if (nextRepo != null && result != null
						&& !result.equals(nextRepo)) {
					if (warn)
						MessageDialog
								.openError(
										shell,
										UIText.RepositoryAction_multiRepoSelectionTitle,
										UIText.RepositoryAction_multiRepoSelection);
					return null;
				}
				result = nextRepo;
			}
		else
			result = mapping.getRepository();
		if (result == null) {
			if (warn)
				MessageDialog.openError(shell,
						UIText.RepositoryAction_errorFindingRepoTitle,
						UIText.RepositoryAction_errorFindingRepo);
			return null;
		}

		return result;
