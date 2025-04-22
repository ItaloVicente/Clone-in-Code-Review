	@NonNull
	private static Map<IPath, RepositoryMapping> getMappings(
			final @Nullable IProject project) {
		GitProjectData data = getProjectData(project);
		if (data == null) {
			return Collections.emptyMap();
		}
		return data.getRepositoryMappings();
	}

