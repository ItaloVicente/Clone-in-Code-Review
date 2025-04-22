	@Nullable
	private static Repository[] getRepositories(boolean warn,
			@NonNull IStructuredSelection selection, Shell shell) {

		Set<Repository> repositories = new HashSet<>();
		Set<Object> elements = getSelectionContents(selection);
		if (GitTraceLocation.SELECTION.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.SELECTION.getLocation(), "selection=" //$NON-NLS-1$
							+ selection + ", elements=" + elements.toString()); //$NON-NLS-1$
		}
		for (Object location : elements) {
			Repository repo = null;
			if (location instanceof Repository) {
				repo = (Repository) location;
			} else if (location instanceof IResource) {
				repo = ResourceUtil.getRepository((IResource) location);
			} else if (location instanceof IPath) {
				repo = ResourceUtil.getRepository((IPath) location);
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

		if (repositories.isEmpty()) {
			if (warn) {
				MessageDialog.openError(shell,
						UIText.RepositoryAction_errorFindingRepoTitle,
						UIText.RepositoryAction_errorFindingRepo);
			}
			return null;
		}

		return repositories.toArray(new Repository[repositories.size()]);
	}

