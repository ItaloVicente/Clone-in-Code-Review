			@Nullable CanonicalTreeParser otherTree, Attributes result)
					throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null
				|| otherTree != null) {
			AttributesNode attributesNode = attributesNode(
					treeWalk, workingTreeIterator, dirCacheIterator, otherTree);
			if (attributesNode != null) {
				mergeAttributes(attributesNode, entryPath, isDirectory, result);
			}
			mergePerDirectoryEntryAttributes(entryPath, isDirectory,
					parentOf(workingTreeIterator), parentOf(dirCacheIterator),
					parentOf(otherTree), result);
