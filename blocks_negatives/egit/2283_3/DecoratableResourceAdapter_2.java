		final ContainerTreeIterator workspaceIterator = treeWalk.getTree(
				T_WORKSPACE, ContainerTreeIterator.class);
		final ResourceEntry resourceEntry = workspaceIterator != null ? workspaceIterator
				.getResourceEntry() : null;

		if (resourceEntry == null)
			return;

		if (workspaceIterator != null && workspaceIterator.isEntryIgnored()) {
			ignored = true;
			return;
		}

		final int mHead = treeWalk.getRawMode(T_HEAD);
		final int mIndex = treeWalk.getRawMode(T_INDEX);

		if (mHead == FileMode.MISSING.getBits()
				&& mIndex == FileMode.MISSING.getBits())
			return;

		tracked = true;

		if (mHead == FileMode.MISSING.getBits()) {
			staged = Staged.ADDED;
		} else if (mIndex == FileMode.MISSING.getBits()) {
			staged = Staged.REMOVED;
		} else if (mHead != mIndex
				|| (mIndex != FileMode.TREE.getBits() && !treeWalk.idEqual(
						T_HEAD, T_INDEX))) {
			staged = Staged.MODIFIED;
		} else {
			staged = Staged.NOT_STAGED;
		}

		final DirCacheIterator indexIterator = treeWalk.getTree(T_INDEX,
				DirCacheIterator.class);
		final DirCacheEntry indexEntry = indexIterator != null ? indexIterator
				.getDirCacheEntry() : null;

		if (indexEntry == null)
			return;

		if (indexEntry.getStage() > 0)
			conflicts = true;

		if (indexEntry.isAssumeValid()) {
			dirty = false;
			assumeValid = true;
		} else {
			if (workspaceIterator != null
					&& workspaceIterator.isModified(indexEntry, true))
				dirty = true;
		}
