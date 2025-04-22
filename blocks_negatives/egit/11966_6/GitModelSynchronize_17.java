	 * Launches Git Model synchronize operation for synchronizing a set of
	 * resources in the workspace with a remote reference.
	 * 
	 * @param file
	 *            the file to synchronize (will also be used to lookup resouce
	 *            mappings)
	 * @param repository
	 *            the repository
	 * @param refName
	 *            the reference to synchronize with
	 * @throws IOException
	 */
	public static final void synchronizeModelWithWorkspace(IFile file,
			Repository repository, String refName) throws IOException {
		synchronizeModel(file, new GitSynchronizeData(repository,
				Constants.HEAD, refName, true));
	}

	/**
	 * Launches Git Model synchronize operation for synchronizing a set of
	 * resources in the workspace with a remote reference.
