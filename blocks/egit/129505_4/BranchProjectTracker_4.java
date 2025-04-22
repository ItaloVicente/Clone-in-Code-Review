		return projectPaths;
	}

	private IProject[] getValidOpenProjects() {
		try {
			return ProjectUtil.getValidOpenProjects(repository);
		} catch (CoreException e) {
			return null;
		}
