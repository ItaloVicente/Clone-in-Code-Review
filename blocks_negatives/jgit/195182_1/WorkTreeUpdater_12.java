		if (repo == null) {
			throw new NullPointerException(JGitText.get().repositoryIsRequired);
		}
		return repo;
	}


	/**
	 * Gets non-null and non-bare repository instance
	 *
	 * @return non-null and non-bare repository instance
	 * @throws java.lang.NullPointerException if the handler was constructed without a repository.
	 * @throws NoWorkTreeException            if the handler was constructed with a bare repository
	 */
	private Repository nonNullNonBareRepo() throws NullPointerException, NoWorkTreeException {
		if (nonNullRepo().isBare()) {
			throw new NoWorkTreeException();
		}
		return repo;
