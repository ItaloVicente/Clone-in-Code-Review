	@Nullable
	private static Repository[] getRepositories(boolean warn,
			@NonNull IStructuredSelection selection, Shell shell) {

		List<Repository> repositories = new ArrayList<>();
		IPath[] locations = getSelectedLocations(selection);
		if (GitTraceLocation.SELECTION.isActive())
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.SELECTION.getLocation(),
					"selection=" //$NON-NLS-1$
							+ selection + ", locations=" //$NON-NLS-1$
							+ Arrays.toString(locations));
		for (IPath location : locations) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(location);
			Repository repo;
			if (mapping != null) {
				repo = mapping.getRepository();
			} else {
				repo = org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryCache().getRepository(location);
			}
			if (repo != null) {
				repositories.add(repo);
			}
		}

		if (repositories.size() == 0) {
			for (Object o : selection.toArray()) {
				Repository nextRepo = AdapterUtils.adapt(o, Repository.class);
				if (nextRepo != null) {
					repositories.add(nextRepo);
				}
			}
		}

		if (repositories.size() == 0) {
			if (warn)
				MessageDialog.openError(shell,
						UIText.RepositoryAction_errorFindingRepoTitle,
						UIText.RepositoryAction_errorFindingRepo);
			return null;
		}

		return repositories.toArray(new Repository[repositories.size()]);
	}

