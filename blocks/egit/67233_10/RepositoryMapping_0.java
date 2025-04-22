		return data;
	}

	@Nullable
	private static RepositoryMapping findMapping(
			final @NonNull IResource resource) {
		GitProjectData data = getProjectData(resource.getProject());
