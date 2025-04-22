	public Repository open(final C req
			throws RepositoryNotFoundException
		if (isUnreasonableName(name))
			throw new RepositoryNotFoundException(name);

		Repository db = exports.get(nameWithDotGit(name));
		if (db != null) {
			db.incrementOpen();
			return db;
