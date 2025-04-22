		IProject[] allProjects = getProjectsForContainerMatch(root);

		for (IProject prj : allProjects)
			if (checkContainerMatch(prj, absFile))
				return prj;

		if (checkContainerMatch(root, absFile))
			return root;

		return null;
	}

	private static IProject[] getProjectsForContainerMatch(IWorkspaceRoot root) {
