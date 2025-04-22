		return baseCommit.getTree();
	}

	protected AbstractTreeIterator mergeBaseIterator() throws IOException {
		RevTree baseTree = mergeBase();
		return baseTree == null ? new EmptyTreeIterator() : openTree(baseTree);
