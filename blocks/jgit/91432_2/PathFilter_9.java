		return matchFilter(walker) <= 0;
	}

	@Override
	public int matchFilter(final TreeWalk walker) {
		return walker.isPathMatch(pathRaw
