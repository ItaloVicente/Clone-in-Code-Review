	/**
	 * Clients should obtain an instance from {@link Activator}
	 */
	RepositoryUtil() {
		workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation()
				.toFile().toPath();
