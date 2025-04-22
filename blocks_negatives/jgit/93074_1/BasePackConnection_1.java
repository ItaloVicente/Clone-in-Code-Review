	/**
	 * Create an exception to indicate problems finding a remote repository. The
	 * caller is expected to throw the returned exception.
	 *
	 * Subclasses may override this method to provide better diagnostics.
	 *
	 * @return a TransportException saying a repository cannot be found and
	 *         possibly why.
	 */
	protected TransportException noRepository() {
		return new NoRemoteRepositoryException(uri, JGitText.get().notFound);
	}

