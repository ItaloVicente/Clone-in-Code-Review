		return matchFilter(walker) == 0;
	}

	@Override
	public int matchFilter(TreeWalk walker)
			throws MissingObjectException
			IOException {
		final int r = a.matchFilter(walker);
		if (r == 0) {
			return 1;
		}
		if (r == 1) {
			return 0;
		}
		return -1;
