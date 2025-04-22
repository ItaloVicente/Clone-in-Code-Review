	 * @return the RepositoryMapping for this project, or null for non
	 *         GitProvider.
	 */
	@Nullable
	public static RepositoryMapping getMapping(@Nullable final IProject project) {
		if (project == null) {
			return null;
		}
		return findMapping(project);
	}

	/**
	 * Get the repository mapping for a project.
	 *
	 * @param resource
	 *            to find the mapping for
	 * @return the RepositoryMapping for this project, or null for non
