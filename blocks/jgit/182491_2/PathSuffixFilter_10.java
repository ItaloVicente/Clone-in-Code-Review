	@Override
	public int matchFilter(TreeWalk walker) throws MissingObjectException
			IncorrectObjectTypeException
		if (walker.isSubtree()) {
			return -1;
		}
		return super.matchFilter(walker);
	}

