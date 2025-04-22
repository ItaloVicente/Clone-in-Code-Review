	private boolean isEqualEntry(AbstractTreeIterator iter1,
			AbstractTreeIterator iter2) {
		if (!iter1.getEntryFileMode().equals(iter2.getEntryFileMode()))
			return false;
		ObjectId id1 = iter1.getEntryObjectId();
		ObjectId id2 = iter2.getEntryObjectId();
		return id1 != null ? id1.equals(id2) : id2 == null;
	}

	/**
	 * Would unstashing overwrite local changes?
	 *
	 * @param stashIndexIter
	 * @param stashWorkingTreeIter
	 * @param headIter
	 * @param indexIter
	 * @param workingTreeIter
	 * @return true if unstash conflict, false otherwise
	 */
	private boolean isConflict(AbstractTreeIterator stashIndexIter,
			AbstractTreeIterator stashWorkingTreeIter,
			AbstractTreeIterator headIter, AbstractTreeIterator indexIter,
			AbstractTreeIterator workingTreeIter) {
		boolean indexDirty = indexIter != null
				&& (headIter == null || !isEqualEntry(indexIter, headIter));

		boolean workingTreeDirty = workingTreeIter != null
				&& (headIter == null || !isEqualEntry(workingTreeIter, headIter));

		if (indexDirty && stashIndexIter != null && indexIter != null
				&& !isEqualEntry(stashIndexIter, indexIter))
			return true;

		if (workingTreeDirty && stashWorkingTreeIter != null
				&& workingTreeIter != null
				&& !isEqualEntry(stashWorkingTreeIter, workingTreeIter))
			return true;

		return false;
	}

	private ObjectId getHeadTree() throws GitAPIException {
		final ObjectId headTree;
		try {
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().cannotReadTree, e);
		}
		if (headTree == null)
			throw new NoHeadException(JGitText.get().cannotReadTree);
		return headTree;
	}

