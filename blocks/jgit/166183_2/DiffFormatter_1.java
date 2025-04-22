		TreeWalk walk = new TreeWalk(repository
		int aIndex = walk.addTree(a);
		int bIndex = walk.addTree(b);
		if (repository != null) {
			if (a instanceof WorkingTreeIterator
					&& b instanceof DirCacheIterator) {
				((WorkingTreeIterator) a).setDirCacheIterator(walk
			} else if (b instanceof WorkingTreeIterator
					&& a instanceof DirCacheIterator) {
				((WorkingTreeIterator) b).setDirCacheIterator(walk
			}
		}
