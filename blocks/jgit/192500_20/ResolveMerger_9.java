	private boolean isWorktreeDirty() throws IOException {
		boolean hasWorkingTree = tw.getTreeCount() > T_FILE;
		WorkingTreeIterator work = tw.getTree(T_FILE
		DirCacheBuildIterator index = tw.getTree(T_INDEX
		if(!hasWorkingTree){
			return false;
		}
		DirCacheEntry oursDce = oursDce(index);
		return isWorktreeDirty(work

	}

