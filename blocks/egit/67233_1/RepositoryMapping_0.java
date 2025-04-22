	public synchronized Repository getSubmoduleRepository(
			@NonNull IResource resource) {
		return getSubmoduleRepository(resource.getProjectRelativePath());
	}

	@Nullable
	public synchronized Repository getSubmoduleRepository(
			IPath projectRelativePath) {
