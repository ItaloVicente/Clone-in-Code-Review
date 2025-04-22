	private AttributesNode getRootAttributesNode() throws IOException {
		WorkingTreeIterator workingTreeIterator = getRootTreeNode(
				WorkingTreeIterator.class);
		DirCacheIterator dirCacheIterator = getRootTreeNode(
				DirCacheIterator.class);
		CanonicalTreeParser other = getRootTreeNode(CanonicalTreeParser.class);
		return getCurrentAttributesNode(operationType
				dirCacheIterator
