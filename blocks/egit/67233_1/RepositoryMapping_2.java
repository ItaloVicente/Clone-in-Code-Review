	@NonNull
	public static Collection<RepositoryMapping> getMappings(@Nullable
	final IProject project) {
		GitProjectData data = getProjectData(project);
		if (data == null) {
			return new ArrayList<RepositoryMapping>();
		}
		return data.getRepositoryMappings();
	}

