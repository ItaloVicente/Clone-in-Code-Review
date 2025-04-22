	/**
	 * Launches Git Model synchronize operation for synchronizing a set of
	 * resources in the workspace with a remote reference.
	 *
	 * @param file
	 *            the file to synchronize (will also be used to lookup resouce
	 *            mappings)
	 * @param repository
	 *            the repository
	 * @param sourceRef
	 *            the source reference
	 * @param destinationRef
	 *            the destination reference
	 * @throws IOException
	 */
	public static final void synchronizeModelBetweenRefs(IFile file,
			Repository repository, String sourceRef, String destinationRef) throws IOException {
		synchronizeModel(file, new GitSynchronizeData(repository,
				sourceRef, destinationRef, false));
	}
