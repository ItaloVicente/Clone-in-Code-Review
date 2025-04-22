		return data.getRepositoryMapping(resource);
	}

	@Nullable
	public static RepositoryMapping getMapping(@Nullable final IProject project) {
		GitProjectData data = getProjectData(project);
