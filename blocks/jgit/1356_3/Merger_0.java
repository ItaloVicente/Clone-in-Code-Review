		RevCommit base = getBaseCommit(aIdx
		return (base == null) ? new EmptyTreeIterator() : openTree(base.getTree());
	}

	public RevCommit getBaseCommit(final int aIdx
			throws IncorrectObjectTypeException
			IOException {
