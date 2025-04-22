	private boolean isWorktreeDirty() throws IOException {
		boolean hasWorkingTree = tw.getTreeCount() > T_FILE;
		WorkingTreeIterator work = tw.getTree(T_FILE
				WorkingTreeIterator.class);
		DirCacheBuildIterator index = tw.getTree(T_INDEX
				DirCacheBuildIterator.class);
		if (!hasWorkingTree) {
			return false;
		}
		DirCacheEntry oursDce = oursDce(index);
		return isWorktreeDirty(work

	}

