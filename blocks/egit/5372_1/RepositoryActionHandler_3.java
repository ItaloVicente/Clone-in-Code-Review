		if (selectedProjects.length > 0)
			return getRepositoriesFor(selectedProjects);
		IStructuredSelection selection = getSelection();
		if (!selection.isEmpty()) {
			Set<Repository> repos = new HashSet<Repository>();
			for (Object o : selection.toArray())
				if (o instanceof Repository)
					repos.add((Repository) o);
				else if (o instanceof PlatformObject) {
					Repository repo = (Repository) ((PlatformObject) o)
							.getAdapter(Repository.class);
					if (repo != null)
						repos.add(repo);
				}
			return repos.toArray(new Repository[repos.size()]);
		}
		return new Repository[0];
