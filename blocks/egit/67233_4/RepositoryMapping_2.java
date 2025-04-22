		return data.getRepositoryMapping(project);
	}

	@NonNull
	public static Map<IPath, RepositoryMapping> getMappings(@Nullable
	final IProject project) {
		GitProjectData data = getProjectData(project);
		if (data == null) {
			return Collections.emptyMap();
		}
		return data.getRepositoryMappings();
