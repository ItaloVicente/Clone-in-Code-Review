	private static TreeFilter getDiffTreeFilterFor(AbstractTreeIterator a
			AbstractTreeIterator b) {
		if (a instanceof DirCacheIterator && b instanceof WorkingTreeIterator)
			return new IndexDiffFilter(0

		if (a instanceof WorkingTreeIterator && b instanceof DirCacheIterator)
			return new IndexDiffFilter(1

		TreeFilter filter = TreeFilter.ANY_DIFF;
		if (a instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(new NotIgnoredFilter(0)
		if (b instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(new NotIgnoredFilter(1)
		return filter;
	}

