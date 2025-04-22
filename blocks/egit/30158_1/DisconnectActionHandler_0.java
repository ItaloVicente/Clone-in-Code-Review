		IProject[] selectedProjects = getProjectsForSelectedResources();
		List<IProject> projects = new ArrayList<IProject>(selectedProjects.length);
		for (IProject project : selectedProjects) {
			if (project.isOpen()
					&& RepositoryProvider.getProvider(project) instanceof GitProvider)
				projects.add(project);
		}
		if (projects.isEmpty())
