		return compute(repository, walk, commit, parents, 0, markTreeFilters);
	}

	public static FileDiff[] compute(final Repository repository,
			final TreeWalk walk, final RevCommit commit,
			final RevCommit[] parents, final int maxNumberOfFiles,
			final TreeFilter... markTreeFilters) throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {
