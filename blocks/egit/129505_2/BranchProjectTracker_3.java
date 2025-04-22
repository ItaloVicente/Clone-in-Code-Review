
		return new ProjectTrackerPreferenceSnapshot(repository, branch,
				projectPaths);
	}

	private List<String> getAssociatedProjectsPaths() {

		IProject[] projects = getValidOpenProjects();
		if (projects == null)
			return null;

		List<String> projectPaths = new ArrayList<>();

