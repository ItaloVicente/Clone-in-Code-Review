	private void getPerDirectoryEntryAttributes(String path, boolean isDir,
			OperationType opType, WorkingTreeIterator workingTreeIterator,
			DirCacheIterator dirCacheIterator, CanonicalTreeParser other,
			Attributes attributes)
			throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null
				|| other != null) {
			AttributesNode currentAttributesNode = getCurrentAttributesNode(
					opType, workingTreeIterator, dirCacheIterator, other);
			if (currentAttributesNode != null) {
				currentAttributesNode.getAttributes(path, isDir, attributes);
			}
			getPerDirectoryEntryAttributes(path, isDir, opType,
					getParent(workingTreeIterator, WorkingTreeIterator.class),
					getParent(dirCacheIterator, DirCacheIterator.class),
					getParent(other, CanonicalTreeParser.class), attributes);
		}
	}

	private static <T extends AbstractTreeIterator> T getParent(T current,
