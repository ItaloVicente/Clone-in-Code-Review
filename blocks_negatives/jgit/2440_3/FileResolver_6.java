	public Repository open(final HttpServletRequest req,
			final String repositoryName) throws RepositoryNotFoundException,
			ServiceNotEnabledException {
		if (isUnreasonableName(repositoryName))
			throw new RepositoryNotFoundException(repositoryName);

		final Repository db;
		try {
			final File gitdir = new File(basePath, repositoryName);
			db = RepositoryCache.open(FileKey.lenient(gitdir, FS.DETECTED), true);
		} catch (IOException e) {
			throw new RepositoryNotFoundException(repositoryName, e);
