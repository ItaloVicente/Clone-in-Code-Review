
		return new ProjectTrackerPreferenceSnapshot(repository, branch,
				projectPaths);
	}

	@NonNull
	private List<String> getAssociatedProjectsPaths() {

		IProject[] projects = getValidOpenProjects();
		if (projects == null) {
			return Collections.emptyList();
		}

		List<String> projectPaths = new ArrayList<>();

