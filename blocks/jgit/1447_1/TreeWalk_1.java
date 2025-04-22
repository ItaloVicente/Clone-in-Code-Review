	private boolean isSkipCurrentEntry() {
		final AbstractTreeIterator ch = currentHead;
		for (final AbstractTreeIterator t : trees)
			if (t.matches == ch && t.isSkipped())
				return true;

		return false;
	}

