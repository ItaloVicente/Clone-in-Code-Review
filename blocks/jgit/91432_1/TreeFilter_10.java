
	public int matchFilter(final TreeWalk walker)
			throws MissingObjectException
			IOException
	{
		return include(walker) ? 0 : 1;
	}

