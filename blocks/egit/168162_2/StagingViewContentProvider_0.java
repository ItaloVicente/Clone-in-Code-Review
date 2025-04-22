			treeRoots = null;
			compactTreeRoots = null;
		}
	}

	void setSortByState(boolean enable) {
		comparator.sortByState = enable;
		if (content != null) {
			Arrays.sort(content, comparator);
			treeRoots = null;
			compactTreeRoots = null;
