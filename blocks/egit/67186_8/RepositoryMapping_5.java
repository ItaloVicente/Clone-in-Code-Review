		if (project == null) {
			return null;
		}
		return findMapping(project);
	}

	@Nullable
	private static RepositoryMapping findMapping(@NonNull
	final IResource resource) {
		final IProject project = resource.getProject();
