		Set<IProject> projects = new HashSet<IProject>();
		for (Repository repo : selectedBranches.keySet())
			projects.addAll(resources.get(repo));

		return projects;
	}

	boolean shouldIncludeLocal() {
		return shouldIncludeLocal;
