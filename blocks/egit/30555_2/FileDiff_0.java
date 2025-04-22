		return compute(repository, walk, commit, 0, markTreeFilters);
	}

	public static FileDiff[] compute(final Repository repository,
			final TreeWalk walk, final RevCommit commit,
			final int maxNumberOfFiles, final TreeFilter... markTreeFilters)
			throws MissingObjectException, IncorrectObjectTypeException,
			CorruptObjectException, IOException {
