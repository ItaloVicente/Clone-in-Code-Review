	/**
	 * Retrieves the {@link Repository}, if any, for a given location.
	 *
	 * @param location
	 *            to find the repository for
	 * @return the {@link Repository}, or {@code null} if none could be
	 *         determined
	 */
	@Nullable
	private static Repository getRepository(@NonNull IPath location) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return org.eclipse.egit.core.Activator.getDefault().getRepositoryCache()
				.getRepository(location);
	}

	/**
	 * Retrieves the {@link Repository}, if any, for a given {@link IResource}.
	 *
	 * @param resource
	 *            to find the repository for
	 * @return the {@link Repository}, or {@code null} if none could be
	 *         determined
	 */
	@Nullable
	private static Repository getRepository(@NonNull IResource resource) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return org.eclipse.egit.core.Activator.getDefault().getRepositoryCache()
				.getRepository(resource);
	}

