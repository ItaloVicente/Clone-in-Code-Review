	private boolean isModifiedSubtree_IndexWorkingtree(String path)
			throws CorruptObjectException
		NameConflictTreeWalk tw = new NameConflictTreeWalk(repo);
		try {
			tw.addTree(new DirCacheIterator(dc));
			tw.addTree(new FileTreeIterator(repo));
			tw.setRecursive(true);
			tw.setFilter(PathFilter.create(path));
			DirCacheIterator dcIt;
			WorkingTreeIterator wtIt;
			while (tw.next()) {
				dcIt = tw.getTree(0
				wtIt = tw.getTree(1
				if (dcIt == null || wtIt == null)
					return true;
				if (wtIt.isModified(dcIt.getDirCacheEntry()
						this.walk.getObjectReader())) {
					return true;
				}
			}
			return false;
		} finally {
			tw.release();
		}
	}

	private boolean isModified_IndexTree(String path
			FileMode iMode
			throws CorruptObjectException
		if (iMode != tMode)
			return true;
		if (FileMode.TREE.equals(iMode)
				&& (iId == null || ObjectId.zeroId().equals(iId)))
			return isModifiedSubtree_IndexTree(path
		else
			return !equalIdAndMode(iId
	}

	private boolean isModifiedSubtree_IndexTree(String path
			throws CorruptObjectException
