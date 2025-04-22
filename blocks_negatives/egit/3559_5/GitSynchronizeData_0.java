
		this.includeLocal = includeLocal;
		repoParentPath = repo.getDirectory().getParentFile().getAbsolutePath();

		projects = new HashSet<IProject>();
		final IProject[] workspaceProjects = ROOT.getProjects();
		for (IProject project : workspaceProjects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repo)
				projects.add(project);
		}

