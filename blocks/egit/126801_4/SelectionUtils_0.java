	public static Repository[] getRepositories(
			@NonNull IStructuredSelection selection) {

		IProject[] selectedProjects = getSelectedProjects(selection);

		if (selectedProjects.length > 0)
			return getRepositoriesFor(selectedProjects);

		if (selection.isEmpty()) {
			return new Repository[0];
		}

		Set<Repository> repos = new LinkedHashSet<>();
		for (Object o : selection.toArray()) {
			Repository repo = AdapterUtils.adapt(o, Repository.class);
			if (repo != null) {
				repos.add(repo);
			} else {
				return new Repository[0];
			}
		}
		return repos.toArray(new Repository[0]);
	}

