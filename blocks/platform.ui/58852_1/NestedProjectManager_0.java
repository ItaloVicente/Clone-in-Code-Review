		locationsToProjects.clear();
		IProject[] knownProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		knownProjectsCount = knownProjects.length;
		for (IProject project : knownProjects) {
			IPath location = project.getLocation();
			if (location != null) {
				locationsToProjects.put(location, project);
			}
