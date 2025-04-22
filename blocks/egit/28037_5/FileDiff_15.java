		return compute(repository, walk, commit, commit.getParents(),
				markTreeFilters);
	}

	public static FileDiff[] compute(final Repository repository,
			final TreeWalk walk, final RevCommit commit,
			final RevCommit[] parents,
			final TreeFilter... markTreeFilters) throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {
