		return data;
	}

	@Nullable
	public static RepositoryMapping getMapping(@Nullable final IProject project) {
		GitProjectData data = getProjectData(project);
