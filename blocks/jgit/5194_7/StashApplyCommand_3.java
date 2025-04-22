	private boolean isConflict(AbstractTreeIterator stashIndexIter
			AbstractTreeIterator stashWorkingTreeIter
			AbstractTreeIterator headIter
			AbstractTreeIterator workingTreeIter) {
		boolean indexDirty = indexIter != null
				&& (headIter == null || !isEqualEntry(indexIter
