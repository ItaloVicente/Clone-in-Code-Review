	public static IProject[] getProjectsUnderPath(final IPath path) {
		IProject[] allProjects = getProjectsForContainerMatch(ResourcesPlugin
				.getWorkspace().getRoot());
		Set<IProject> projects = new HashSet<IProject>();
		for (IProject p : allProjects)
			if (path.isPrefixOf(p.getLocation()))
				projects.add(p);
		return projects.toArray(new IProject[projects.size()]);
	}

